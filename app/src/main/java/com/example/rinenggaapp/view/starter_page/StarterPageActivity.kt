package com.example.rinenggaapp.view.starter_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R

class StarterPageActivity : AppCompatActivity() {

    private lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter_page)

        prefs = getSharedPreferences("starterPage", Context.MODE_PRIVATE)
        Log.d("prefs", prefs.getString("skipStarterPage", null).toString())
        if (prefs.getString("skipStarterpage", null).toString() == "SKIP") {
            startActivity(Intent(this, MainActivity::class.java))
        }
        loadFragment(StarterPageFragment_1())



    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_starter_page,fragment)
        transaction.commit()
    }
}