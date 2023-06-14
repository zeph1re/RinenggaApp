package com.example.rinenggaapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account (
    val name : String,
    val nis : String,
    val email : String,
    val password : String
        ) : Parcelable

{
}