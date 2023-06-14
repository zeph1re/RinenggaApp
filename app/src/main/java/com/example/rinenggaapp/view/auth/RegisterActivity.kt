package com.example.rinenggaapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.ActivityLoginBinding
import com.example.rinenggaapp.databinding.ActivityRegisterBinding
import com.example.rinenggaapp.view.home.HomeViewModel
import com.example.rinenggaapp.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

//    private var registerViewModel : AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.registerButton.setOnClickListener {
            val fullName = binding.fullNameInputRegister.text.toString()
            val nis = binding.nisInputRegister.text.toString()
            val email = binding.emailInputRegister.text.toString()
            val password = binding.passwordInputRegister.text.toString()
            val rePassword = binding.rePasswordInputRegister.text.toString()


            if (fullName.isNotEmpty() && nis.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()) {
                if (password == rePassword) {
                    startActivity(Intent(this, LoginActivity::class.java))

                } else {
                    Toast.makeText(this, "Password dan Re-enter Password harus sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ada field yang Kosong!!", Toast.LENGTH_SHORT).show()
            }


        }


    }
}