package com.example.rinenggaapp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.ActivityHomeDetailBinding
import com.example.rinenggaapp.databinding.ActivityMainBinding
import com.example.rinenggaapp.model.Module
import com.example.rinenggaapp.viewmodel.ModuleViewModel
import kotlinx.coroutines.launch

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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}