package com.example.rinenggaapp.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseCloudStorage = Firebase.storage


    private val currentUser = MutableLiveData<FirebaseUser>()
    val currentUserLiveData = currentUser

    private val currentUserProfile = MutableLiveData<User>()
    val currentUserProfileLiveData : LiveData<User> = currentUserProfile

    private val getSpesificUserById = MutableLiveData<User?>()
    val getSpesificUserByIdLiveData : LiveData<User?> = getSpesificUserById

    private val currentUserFotoProfilUrl = MutableLiveData<String?>()
    val currentUserFotoProfilUrlLiveData: LiveData<String?> = currentUserFotoProfilUrl

    private val updatePasswordStatus = MutableLiveData<String>()
    val updatePasswordStatusLiveData : LiveData<String> = updatePasswordStatus

    private val updateProfileStatus = MutableLiveData<String>()
    val updateProfileStatusLiveData : LiveData<String> = updateProfileStatus


    fun getSpecificUserById(userId: String) {
        val user = firebaseAuth.currentUser
        firestore.collection("user")
            .document(user!!.uid)
            .get()
            .addOnSuccessListener { snapshot ->
                val profile = snapshot.toObject(User::class.java)
                getSpesificUserById.postValue(profile)
            }
            .addOnFailureListener {
                getSpesificUserById.value = null
            }
    }

    suspend fun changePassword(password : String){
        firebaseAuth.currentUser!!.updatePassword(password).addOnCompleteListener {
            if (it.isSuccessful) {
                updatePasswordStatus.postValue("UPDATED")
            } else {
                updatePasswordStatus.postValue("FAILED")
            }
        }
    }

    suspend fun editProfile(userProfile : User){
        val user = firebaseAuth.currentUser
        if (user != null) {
            firestore.collection("user").document(user.uid)
                .update(
                    mapOf(
                        "name" to userProfile.name,
                        "nis" to userProfile.nis,
                        "no_hp" to userProfile.no_hp,
                        "email" to userProfile.email
                        )
                ).addOnSuccessListener {
                    firestore.collection("user")
                        .document(currentUser.value!!.uid)
                        .get()
                        .addOnSuccessListener {snapshot ->
                            val profile = snapshot.toObject(User::class.java)
                            currentUserProfile.postValue(profile!!)
                            updateProfileStatus.postValue("OK")
                        }
                }.addOnFailureListener {
                    updateProfileStatus.postValue("FAILED")
                }
        }

    }

    suspend fun getUserProfilePhotoUrl(fileUri: Uri?, isUploadingImage: Boolean, namaFile: String?){
        if(isUploadingImage){
            val storageRef = firebaseCloudStorage.reference
            val newPhotoRef = storageRef.child("foto_profil_user/$namaFile")
            val uploadTask = newPhotoRef.putFile(fileUri!!)
            uploadTask.continueWithTask { getDownloadUrlTask ->
                if(!getDownloadUrlTask.isSuccessful){
                    getDownloadUrlTask.exception?.let {
                        throw it
                    }
                }
                newPhotoRef.downloadUrl
            }.addOnCompleteListener {  getDownloadTaskStatus ->
                if(getDownloadTaskStatus.isSuccessful){
                    currentUserFotoProfilUrl.postValue(getDownloadTaskStatus.result.toString())
                }else{
                    currentUserFotoProfilUrl.postValue(null)
                }
            }
        }else{
            firestore.collection("user")
                .document(currentUser.value!!.uid)
                .get()
                .addOnSuccessListener { snapshot ->
                    val userProfile = snapshot.toObject(User::class.java)
                    currentUserFotoProfilUrl.postValue(userProfile!!.imageUrl)
                }
        }
    }


    companion object {
        @Volatile
        private var instance : UserRepository? = null
        fun getInstance() : UserRepository {
            return instance ?: synchronized(this) {
                if (instance == null ){
                    instance = UserRepository()
                }
                return instance as UserRepository
            }
        }
    }
}

