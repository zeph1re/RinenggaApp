package com.example.rinenggaapp.view.assignment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.databinding.ActivityAssignmentBinding
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class AssignmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssignmentBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getClassName () : String {
        val className = intent.getStringExtra("classInfo")
        return className.toString()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }

}