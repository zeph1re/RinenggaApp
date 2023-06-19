package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.Question
import com.example.rinenggaapp.repository.QuestionRepository

class QuestionViewModel : ViewModel() {


    private val questionRepository = QuestionRepository.getInstance()

    private val question = questionRepository.questionLiveData

    suspend fun getAllQuestion() = questionRepository.getAllQuestion()


}