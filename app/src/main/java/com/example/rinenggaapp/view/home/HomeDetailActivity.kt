package com.example.rinenggaapp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rinenggaapp.view.MainActivity
import com.example.rinenggaapp.databinding.ActivityHomeDetailBinding
import com.example.rinenggaapp.model.Module

class HomeDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getModuleDetail(): Module? {
        return intent.getParcelableExtra("MODULE") as Module?
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}