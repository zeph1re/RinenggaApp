package com.example.rinenggaapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.repository.QuestionRepository

class QuestionViewModel : ViewModel() {
    private val questionRepository = QuestionRepository.getInstance()

    val allQuestion = questionRepository.listQuestionLiveData
    val quizQuestion = questionRepository.listQuizQuestionLiveData

    suspend fun getAllQuestion() = questionRepository.getAllQuestion()
    suspend fun getQuestionbyModuleName(moduleName : String) = questionRepository.getAllQuestionByModuleName(moduleName)




}