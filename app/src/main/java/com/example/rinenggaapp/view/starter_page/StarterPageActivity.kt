package com.example.rinenggaapp.view.starter_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R

class StarterPageActivity : AppCompatActivity() {

    private lateinit var prefs : SharedPreferences
    private lateinit var skip : String
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter_page)

        skip = ""
        prefs = getSharedPreferences("starterPage", Context.MODE_PRIVATE)
        Log.d("prefs", prefs.getString("skipStarterPage", skip).toString())
        if (prefs.getString("skipStarterpage", skip).toString() == "SKIP") {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}