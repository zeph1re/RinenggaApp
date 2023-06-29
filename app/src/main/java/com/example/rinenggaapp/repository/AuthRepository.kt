package com.example.rinenggaapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.User
import com.example.rinenggaapp.model.UserLogin
import com.example.rinenggaapp.model.UserRegister
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AuthRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseCloudStorage = Firebase.storage


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



    companion object {
        @Volatile
        private var instance : AuthRepository? = null
        fun getInstance() : AuthRepository {
            return instance ?: synchronized(this) {
                if (instance == null ){
                    instance = AuthRepository()
                }
                return instance as AuthRepository
            }
        }
    }

}
