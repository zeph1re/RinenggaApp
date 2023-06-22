package com.example.rinenggaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val id : String = "",
    val moduleName: String = "",
    val questionText: String = "",
    val answers : ArrayList<String>? = null ,
    val correctAnswerIndex : Int = 0
) : Parcelable
