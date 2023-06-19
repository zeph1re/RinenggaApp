package com.example.rinenggaapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.UserRegister
import com.example.rinenggaapp.model.User
import com.example.rinenggaapp.model.UserLogin
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

    suspend fun loginUser(userLogin : UserLogin) {
        firebaseAuth.signInWithEmailAndPassword(
            userLogin.email, userLogin.password
        ).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                currentUser.value = firebaseAuth.currentUser
                firestore.collection("user")
                    .document(currentUser.value!!.uid)
                    .get()
                    .addOnSuccessListener { dataUser ->
                        val user = dataUser.toObject(User::class.java)
                        currentUserProfile.value = user
                        loginStatus.postValue("OK")

                    }

            } else {
                Log.w(TAG, "signInWithCustomToken:failure", task.exception)

            }
        }

    }

    suspend fun registerUser(userRegister : UserRegister) : MutableLiveData<String> {
        val resultMessage = MutableLiveData<String>()
        firebaseAuth.createUserWithEmailAndPassword(userRegister.email, userRegister.password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d("Current User", user.toString())
                    val updateDisplayNameUser = userProfileChangeRequest {
                        displayName = userRegister.name
                    }
                    user!!.updateProfile(updateDisplayNameUser)
                        .addOnCompleteListener {task ->
                        if (task.isSuccessful) {
                            val dataUser = User(
                                id = user.uid,
                                name = user.displayName!!,
                                email = user.email!!,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            firestore.collection("user")
                                .document(dataUser.id.toString())
                                .set(dataUser)
                                .addOnCompleteListener {
                                    if (it.isSuccessful){
                                        currentUser.postValue(firebaseAuth.currentUser)
                                        resultMessage.postValue("FAILED")
                                    }
                                }
                        } else {
                            resultMessage.postValue("FAILED")
                        }
                    }
                } else {
                    resultMessage.postValue("FAILED")
                }
            }
        return resultMessage
    }

    suspend fun logout() {
        firebaseAuth.signOut()
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
