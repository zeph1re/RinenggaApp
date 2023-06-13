package com.example.rinenggaapp.model

data class Question(
    val id : String,
    val question: String,
    val optionOne : String,
    val optionTwo : String,
    val optionThree : String,
    val optionFour : String,
    val correctAnswer : String
)
