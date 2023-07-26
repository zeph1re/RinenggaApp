package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.UserLogin
import com.example.rinenggaapp.model.UserRegister
import com.example.rinenggaapp.repository.UserRepository
import javax.inject.Singleton

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository.getInstance()

//    User
    val currentUser = userRepository.currentUserLiveData
    val updateAssignmentScore = userRepository.updateAssignmentScoreStatusLiveData
    val changePasswordStatus = userRepository.updatePasswordStatusLiveData
    val updateProfileStatus = userRepository.updateProfileStatusLiveData


//    Auth
    val loginStatus = userRepository.loginStatusLiveData
    val registerStatus = userRepository.registerStatusLiveData
    val checkEmailRegistered = userRepository.checkIfEmailRegisterLiveData
    val currentUserProfile = userRepository.currentUserProfileLiveData

    suspend fun registerAccount (account: UserRegister) = userRepository.registerUser(account)
    suspend fun checkEmailAlreadyRegistered (email : String) = userRepository.checkEmailAlreadyRegister(email)
    suspend fun loginAccount (userLogin : UserLogin) = userRepository.loginUser(userLogin)
    suspend fun logout() = userRepository.logout()
    suspend fun changePassword(oldPassword : String, newPassword: String)  = userRepository.changePassword(oldPassword, newPassword)
    @Singleton
    suspend fun editProfile(fullName : String, nis : String, email : String, phoneNumber : String) = userRepository.editProfile(fullName,nis,email,phoneNumber)
    suspend fun putAssignmentData(assignmentScore : Int, classInfo : String) = userRepository.putAssignmentData(assignmentScore, classInfo)

}