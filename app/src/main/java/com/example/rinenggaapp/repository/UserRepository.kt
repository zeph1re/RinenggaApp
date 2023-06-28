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

    private val currentUserFotoProfilUrl = MutableLiveData<String?>()
    val currentUserFotoProfilUrlLiveData: LiveData<String?> = currentUserFotoProfilUrl

    private val currentUser = MutableLiveData<FirebaseUser>()
    val currentUserLiveData = currentUser
    private val getSpesificUserById = MutableLiveData<User>()
    val getSpesificUserByIdLiveData : LiveData<User> = getSpesificUserById


    fun getSpesificUserById(userId: String) {
        firestore.collection("user")
            .document(userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val profile = snapshot.toObject(User::class.java)
                getSpesificUserById.postValue(profile!!)
            }
            .addOnFailureListener {
                getSpesificUserById.value = null
            }
    }

    suspend fun changePassword(){}

    suspend fun editProfile(){}

    suspend fun getUserProfilePhotoUrl(fileUri: Uri?, isUploadingImage: Boolean, namaFile: String?){
        if(isUploadingImage){
            val storageRef = firebaseCloudStorage.reference
            val fotoBaruRef = storageRef.child("foto_profil_user/$namaFile")
            val uploadTask = fotoBaruRef.putFile(fileUri!!)
            uploadTask.continueWithTask { getDownloadUrlTask ->
                if(!getDownloadUrlTask.isSuccessful){
                    getDownloadUrlTask.exception?.let {
                        throw it
                    }
                }
                fotoBaruRef.downloadUrl
            }.addOnCompleteListener {  getDownloadTaskStatus ->
                if(getDownloadTaskStatus.isSuccessful){
                    currentUserFotoProfilUrl.postValue(getDownloadTaskStatus.result.toString())
                }else{
                    currentUserFotoProfilUrl.postValue(null)
                }
            }
        }else{
            firestore.collection("user_profile")
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