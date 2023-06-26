package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.User
import com.example.rinenggaapp.repository.UserRepository

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository.getInstance()
    val currentUserProfile = userRepository.currentUserFotoProfilUrlLiveData
//    val currentUser = userRepository.currentUser

    suspend fun getCurrentUser() {

    }

    suspend fun changePassword() {

    }

    suspend fun editProfile(user : User) {

    }
}