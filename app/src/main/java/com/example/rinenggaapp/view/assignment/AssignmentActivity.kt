package com.example.rinenggaapp.view.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rinenggaapp.databinding.ActivityAssignmentBinding

class AssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssignmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getClassName () : String {
        val className = intent.getBundleExtra("className")
        return className.toString()
    }

    override fun onBackPressed() {

    }

}