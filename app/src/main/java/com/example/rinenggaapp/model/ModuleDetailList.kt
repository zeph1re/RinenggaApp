package com.example.rinenggaapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList

@Parcelize
data class ModuleDetailList(
    val definition : String = "",
    val example : List<String>? = null,
) : Parcelable
