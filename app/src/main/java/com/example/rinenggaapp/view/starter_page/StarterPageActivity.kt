package com.example.rinenggaapp.view.starter_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rinenggaapp.databinding.ActivityStarterPageBinding
import com.example.rinenggaapp.view.auth.LoginActivity

class StarterPageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStarterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val skipped = prefs.getString("SKIP_KEY", null)
        if (skipped == "SKIP") {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }



}