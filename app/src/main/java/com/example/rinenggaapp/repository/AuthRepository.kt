package com.example.rinenggaapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.Account
import com.example.rinenggaapp.model.User
import com.example.rinenggaapp.model.UserLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth

class AuthRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val currentUser = MutableLiveData<FirebaseUser>()
    val currentUserLiveData = currentUser

    suspend fun loginUser(userLogin : UserLogin) {
        firebaseAuth.signInWithEmailAndPassword(
            userLogin.email, userLogin.password
        ).addOnCompleteListener {
            if(it.isSuccessful) {
                currentUser.value = firebaseAuth.currentUser
                firestore.collection("user")
                    .document(currentUser.value!!.uid)
                    .get()
                    .addOnSuccessListener {
                        val profile = it.toObject(User::class.java)
                    }

            } else {
                Log.w(TAG, "signInWithCustomToken:failure", it.exception)

            }
        }

    }

    suspend fun registerUser(account : Account) : MutableLiveData<String> {

        val resultMessage = MutableLiveData<String>()
        firebaseAuth.createUserWithEmailAndPassword(account.email, account.password)
            .addOnCompleteListener {createAcc ->
                if (createAcc.isSuccessful) {
                    val user = firebaseAuth.currentUser

                }

            }



        return TODO("Provide the return value")
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