package com.example.rinenggaapp.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.Question
import com.google.firebase.firestore.FirebaseFirestore

class QuestionRepository {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val listQuestion = MutableLiveData<List<Question>>()
    val listQuestionLiveData: LiveData<List<Question>> = listQuestion

    private val listQuizQuestion = MutableLiveData<List<Question>>()
    val listQuizQuestionLiveData: LiveData<List<Question>> = listQuizQuestion

    fun getAllQuestion() {
        firebaseFirestore.collection("question")
            .addSnapshotListener { value, _ ->
                val questionList: MutableList<Question> = mutableListOf()
                if (!value!!.isEmpty) {
                    value.forEach { item ->
                        val question = item.toObject(Question::class.java)
                        questionList += question
                    }
                }
                listQuestion.postValue(questionList)
            }
    }


        fun getAllQuestionByModuleName(moduleName: String) {
            firebaseFirestore.collection("question")
                .whereEqualTo("moduleName", moduleName)
                .addSnapshotListener { value, _ ->
                    val questionList: MutableList<Question> = mutableListOf()
                    if (!value!!.isEmpty) {
                        value.forEach { item ->
                            val question = item.toObject(Question::class.java)
                            questionList += question
                        }
                    }
                    listQuizQuestion.postValue(questionList)
                }

        }

        companion object {
            @Volatile
            private var instance: QuestionRepository? = null
            fun getInstance(): QuestionRepository {
                return instance ?: synchronized(this) {
                    if (instance == null) {
                        instance = QuestionRepository()
                    }
                    return instance as QuestionRepository
                }
            }
        }
    }
