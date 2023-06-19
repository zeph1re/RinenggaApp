package com.example.rinenggaapp.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.databinding.ActivityRegisterBinding
import com.example.rinenggaapp.model.UserRegister
import com.example.rinenggaapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRegisterBinding

    private val registerViewModel : AuthViewModel by viewModels()
    val binding get() = _binding

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
                    lifecycleScope.launch {
                        registerViewModel.registerAccount(UserRegister(fullName, nis ,email, password))
                    }

                    startActivity(Intent(this, RegisterVerificationActivity::class.java))

                } else {
                    Toast.makeText(this, "Password dan Re-enter Password harus sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ada field yang Kosong!!", Toast.LENGTH_SHORT).show()
            }


        }


    }
}