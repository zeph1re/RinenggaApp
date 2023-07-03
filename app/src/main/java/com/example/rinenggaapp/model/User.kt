package com.example.rinenggaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class User(
    val id: String? = "",
    val name: String = "",
    val email: String = "",
    val nis: String? = "",
    val classInfo : String? = "",
    val imageUrl: String? = "",
    val no_hp: String? = "",
    val assignmentResult: Int? = 0
): Parcelable
