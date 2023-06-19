package com.example.rinenggaapp.datastore

import android.content.Context

class SharedPreferences (activity : Context){

    val login = "login"
    val SKIP = "SKIP"
    val PREFS = "my_prefs"

    val preferences = activity.getSharedPreferences(PREFS, Context.MODE_PRIVATE)



    fun setStarterPage (skip : String) {
        val prefEditor = preferences.edit()
        prefEditor.putString(SKIP, skip)
        prefEditor.apply()
    }

//    fun getSkippedStarterPage() : String {
//        val skipped = ""
//        skipped = preferences.getString(SKIP, "")
//        return skipped
//    }

}