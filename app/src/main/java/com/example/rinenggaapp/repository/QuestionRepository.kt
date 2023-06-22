package com.example.rinenggaapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.Module
import com.example.rinenggaapp.model.Question
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class QuestionRepository {

    val firebaseFirestore = FirebaseFirestore.getInstance()

    private val listQuestion = MutableLiveData<List<Question>>()
    val listQuestionLiveData : LiveData<List<Question>> = listQuestion

    private val listQuizQuestion = MutableLiveData<List<Question>>()
    val listQuizQuestionLiveData : LiveData<List<Question>> = listQuizQuestion

//    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

    fun getAllQuestion() {
        firebaseFirestore.collection("question")
            .get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var question : MutableList<Question> = mutableListOf()
                question = task.result.toObjects(Question::class.java)
                Log.d("questionData", question.toString())
            }
        }
    }

    fun getAllQuestionbyModuleName(moduleName : String){
        firebaseFirestore.collection("question")
            .whereEqualTo("moduleName", moduleName)
            .addSnapshotListener { value, error ->
                val questionList : MutableList<Question> = mutableListOf()
                if(!value!!.isEmpty){
                    value.forEach { item ->
                        val question = item.toObject(Question::class.java)
                        Log.d("value question by module name" , question.toString())
                        questionList += question
                    }
                }
                listQuizQuestion.postValue(questionList)
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