package com.example.rinenggaapp.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.User
import com.example.rinenggaapp.model.UserLogin
import com.example.rinenggaapp.model.UserRegister
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseCloudStorage = Firebase.storage

    private val currentUserFotoProfilUrl = MutableLiveData<String?>()
    val currentUserFotoProfilUrlLiveData: LiveData<String?> = currentUserFotoProfilUrl

    private val updatePasswordStatus = MutableLiveData<String>()
    val updatePasswordStatusLiveData : LiveData<String> = updatePasswordStatus

    private val updateProfileStatus = MutableLiveData<String>()
    val updateProfileStatusLiveData : LiveData<String> = updateProfileStatus


    private val currentUser = MutableLiveData<FirebaseUser>()
    val currentUserLiveData = currentUser

    private val checkIfEmailRegister = MutableLiveData<String>()
    val checkIfEmailRegisterLiveData : LiveData<String> = checkIfEmailRegister

    private val currentUserProfile = MutableLiveData<User?>()
    val currentUserProfileLiveData = currentUserProfile

    private val loginStatus = MutableLiveData<String>()
    val loginStatusLiveData: LiveData<String> = loginStatus

    private val registerStatus = MutableLiveData<String>()
    val registerStatusLiveData: LiveData<String> = registerStatus

    private val updateAssignmentScoreStatus = MutableLiveData<String>()
    val updateAssignmentScoreStatusLiveData : LiveData<String> = updateAssignmentScoreStatus



    suspend fun loginUser(userLogin : UserLogin) {
        firebaseAuth.signInWithEmailAndPassword(
            userLogin.email, userLogin.password
        ).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                currentUser.value = firebaseAuth.currentUser
                if (currentUser.value!!.isEmailVerified) {
                    firestore.collection("user")
                        .document(currentUser.value!!.uid)
                        .get()
                        .addOnSuccessListener { dataUser ->
                            val user = dataUser.toObject(User::class.java)
                            currentUserProfile.value = user
                            loginStatus.postValue("OK")
                        }
                } else {
                    loginStatus.postValue("REGISTERED")
                }
            } else {
                loginStatus.postValue("FAILED")
            }
        }

    }

    suspend fun registerUser(userRegister : UserRegister) {
        firebaseAuth.createUserWithEmailAndPassword(userRegister.email, userRegister.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val updateDisplayNameUser = userProfileChangeRequest {
                        displayName = userRegister.name
                    }
                    user!!.updateProfile(updateDisplayNameUser)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val dataUser = User(
                                    id = user.uid,
                                    name = user.displayName!!,
                                    email = user.email!!,
                                    nis = userRegister.nis,
                                    imageUrl = null,
                                    no_hp = null,
                                    assignmentResult = null
                                )
                                firestore.collection("user")
                                    .document(dataUser.id.toString())
                                    .set(dataUser)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            currentUser.postValue(firebaseAuth.currentUser)
                                            registerStatus.postValue("OK")
                                        }
                                    }
                                user.sendEmailVerification()
                            } else {
                                registerStatus.postValue("FAILED")
                            }
                        }
                } else {
                    registerStatus.postValue("FAILED")
                }

            }
    }

    suspend fun logout() {
        firebaseAuth.signOut()
    }

    suspend fun checkEmailAlreadyRegister(email : String) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val check = !task.result.signInMethods.isNullOrEmpty()
                    if(!check) {
                        checkIfEmailRegister.postValue("OK")
                    } else {
                        checkIfEmailRegister.postValue("ALREADY REGISTERED")
                    }
                }
            }
    }


    suspend fun changePassword(oldPassword : String, newPassword : String){
        val user = currentUser.value
        val credential : AuthCredential = EmailAuthProvider.getCredential(user!!.email!!, oldPassword)

        user.reauthenticate(credential)
            .addOnSuccessListener {
                firebaseAuth.currentUser!!.updatePassword(newPassword).addOnCompleteListener {
                    if (it.isSuccessful) {
                        updatePasswordStatus.postValue("UPDATED")
                    } else {
                        updatePasswordStatus.postValue("FAILED")
                    }
                }
            }.addOnFailureListener {
                updatePasswordStatus.postValue("OLD PASSWORD WRONG")
        }
    }

    suspend fun editProfile(fullName : String, nis: String, email : String, phoneNumber: String){
        val user = firebaseAuth.currentUser
        if (user != null) {
            firestore.collection("user").document(user.uid)
                .update(
                    mapOf(
                        "name" to fullName,
                        "nis" to nis,
                        "no_hp" to phoneNumber,
                        "email" to email
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

    suspend fun putAssignmentScore(assignmentScore : Int) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            firestore.collection("user")
                .document(user.uid)
                .update(
                    mapOf(
                        "assignmentScore" to assignmentScore
                    )
                ).addOnSuccessListener {
                    firestore.collection("user")
                        .document(currentUser.value!!.uid)
                        .get()
                        .addOnSuccessListener {snapshot ->
                            val profile = snapshot.toObject(User::class.java)
                            currentUserProfile.postValue(profile!!)
                            updateAssignmentScoreStatus.postValue("OK")
                        }
                }.addOnFailureListener {
                    updateAssignmentScoreStatus.postValue("FAILED")
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

