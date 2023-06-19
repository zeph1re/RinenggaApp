package com.example.rinenggaapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.ActivityHomeDetailBinding
import com.example.rinenggaapp.databinding.ActivityMainBinding
import com.example.rinenggaapp.model.Module

class HomeDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getModuleDetail () : Module? {
        val moduleDetail = intent.getParcelableExtra("MODULE") as Module?
        return moduleDetail
    }
}