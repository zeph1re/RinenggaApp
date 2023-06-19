package com.example.rinenggaapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Module(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val detailModule: ModuleDetailList? = null,
) : Parcelable

