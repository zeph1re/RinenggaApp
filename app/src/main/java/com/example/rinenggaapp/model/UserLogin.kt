package com.example.rinenggaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class UserLogin (
    val email : String = "",
    val password : String = ""
        ) : Parcelable