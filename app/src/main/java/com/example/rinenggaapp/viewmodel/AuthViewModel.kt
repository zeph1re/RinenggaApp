package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.UserLogin
import com.example.rinenggaapp.model.UserRegister
import com.example.rinenggaapp.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository.getInstance()

    val loginStatus = authRepository.loginStatusLiveData
    val registerStatus = authRepository.registerStatusLiveData
    val checkEmailRegistered = authRepository.checkIfEmailRegisterLiveData
    val currentUserProfile = authRepository.currentUserProfileLiveData

    suspend fun registerAccount (account: UserRegister) = authRepository.registerUser(account)

    suspend fun checkEmailAlreadyRegistered (email : String) = authRepository.checkEmailAlreadyRegister(email)
    suspend fun loginAccount (userLogin : UserLogin) = authRepository.loginUser(userLogin)
    suspend fun logout() = authRepository.logout()
}