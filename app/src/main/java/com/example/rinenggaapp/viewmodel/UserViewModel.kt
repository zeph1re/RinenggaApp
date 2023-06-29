package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.User
import com.example.rinenggaapp.repository.UserRepository

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository.getInstance()
    val currentUserProfile = userRepository.currentUserProfileLiveData
    val userSpecific = userRepository.getSpesificUserByIdLiveData

    suspend fun getCurrentUser(userId : String) = userRepository.getSpecificUserById(userId)

    suspend fun changePassword(password : String)  = userRepository.changePassword(password)

    suspend fun editProfile(user : User) = userRepository.editProfile(user)
}