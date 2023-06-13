package com.example.rinenggaapp.model

import java.io.Serializable


data class Module(
    val id : String = "",
    val name : String = "",
    val imageUrl : String = ""
) : Serializable
