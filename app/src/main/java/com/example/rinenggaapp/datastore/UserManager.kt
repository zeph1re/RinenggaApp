package com.example.rinenggaapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore

import java.util.prefs.Preferences

private val Context.dataStore by preferencesDataStore("settings")
class UserManager (context : Context){



    companion object {
        const val APP_PREFERENCES = "app_preferences"
    }



}