package com.example.rinenggaapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseCloudStorage = Firebase.storage

    private val currentUserFotoProfilUrl = MutableLiveData<String>()
    val currentUserFotoProfilUrlLiveData: LiveData<String> = currentUserFotoProfilUrl

    private val currentUser = MutableLiveData<FirebaseUser>()
    val currentUserLiveData = currentUser


    suspend fun getUserData() {}


    suspend fun changePassword(){}

    suspend fun editProfile(){}

    suspend fun uploadImageProfile(){}


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