package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.Account
import com.example.rinenggaapp.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository.getInstance()

    val currentUser = authRepository.currentUserLiveData

    suspend fun registerAccount (account: Account) = authRepository.registerUser(account)

}