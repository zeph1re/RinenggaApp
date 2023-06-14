package com.example.rinenggaapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkFields()

        binding.registerNav.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        binding.loginButton.setOnClickListener {
            processLogin()
        }
    }

    fun checkFields() {
        val email = binding.emailInputLogin
        val password = binding.passwordInputLogin
        binding.loginButton.isEnabled = false
//        val loginFields = listOf(email, password)

    }

    fun processLogin() {
        val email = binding.emailInputLogin.text.toString()
        val password = binding.passwordInputLogin.text.toString()
        
        if (email.isNotEmpty() && password.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }


    }
}