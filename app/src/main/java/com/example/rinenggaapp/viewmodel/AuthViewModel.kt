package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.EmailVerification
import com.example.rinenggaapp.model.UserLogin
import com.example.rinenggaapp.model.UserRegister
import com.example.rinenggaapp.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository.getInstance()

    val loginStatus = authRepository.loginStatusLiveData

    suspend fun registerAccount (account: UserRegister) = authRepository.registerUser(account)
    suspend fun loginAccount (userLogin : UserLogin) = authRepository.loginUser(userLogin)
    suspend fun saveOtpEmail(emailVerification : EmailVerification) = authRepository

    suspend fun logout() = authRepository.logout()
}