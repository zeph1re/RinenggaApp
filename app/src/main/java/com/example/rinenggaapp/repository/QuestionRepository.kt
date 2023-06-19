package com.example.rinenggaapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.Question
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class QuestionRepository {

    val firebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var questionId : String

    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

    private val question = MutableLiveData<List<Question>>()
    val questionLiveData : LiveData<List<Question>> = question

    suspend fun getAllQuestion() {
        firebaseFirestore.collection("question")
            .document(questionId)
            .get()
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    val questionResult = task.getResult().toObject(Question::class.java)
                } else {

                }
            }
    }

    companion object {
        @Volatile
        private var instance: QuestionRepository? = null
        fun getInstance(): QuestionRepository{
            return instance ?: synchronized(this) {
                if (instance == null) {
                    instance = QuestionRepository()
                }
                return instance as QuestionRepository
            }
        }
    }
}