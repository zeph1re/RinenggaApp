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
import com.example.rinenggaapp.view.auth.LoginActivity

class StarterPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter_page)

//        val prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
//        val skipped = prefs.getString("SKIP", "")
//        if (skipped!!.isNotEmpty()) {
//            startActivity(Intent(this, MainActivity::class.java))
//        }

    }
}