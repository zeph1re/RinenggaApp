package com.example.rinenggaapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.databinding.ActivityRegisterBinding
import com.example.rinenggaapp.model.UserRegister
import com.example.rinenggaapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityRegisterBinding

    val binding get() = _binding

    private lateinit var fullNameInput : EditText
    private lateinit var nisInput : EditText
    private lateinit var emailInput : EditText
    private lateinit var passwordInput : EditText
    private lateinit var confirmPasswordInput : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val registerViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        fullNameInput = binding.fullNameInputRegister
        nisInput = binding.nisInputRegister
        emailInput = binding.emailInputRegister
        passwordInput = binding.passwordInputRegister
        confirmPasswordInput = binding.rePasswordInputRegister

        binding.registerButton.setOnClickListener {

            val fullName = fullNameInput.text.toString()
            val nis = nisInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()


            if (checkAllFields()) {
                lifecycleScope.launch {
                    registerViewModel.checkEmailAlreadyRegistered(email)
                    registerViewModel.registerAccount(UserRegister(fullName,nis,email, password))
                }

                registerViewModel.checkEmailRegistered.observe(this) { checkEmailStatus ->
                    when (checkEmailStatus) {
                        "OK" -> {
                            registerViewModel.registerStatus.observe(this) { registerStatus ->
                                when (registerStatus) {
                                    "OK" -> {
                                        Toast.makeText(
                                            this,
                                            "Register Berhasil, silahkan cek email verifikasi anda" +
                                                    "",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        startActivity(Intent(this, LoginActivity::class.java))
                                    }
                                    "FAILED" -> {
                                        Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                        }
                        "ALREADY REGISTERED" -> {
                                Toast.makeText(
                                    this,
                                    "Email sudah terdaftar",
                                    Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            }
        }

    }

    private fun checkAllFields() : Boolean {
        if (fullNameInput.text.toString() == "") {
            fullNameInput.error = "This is required field"
            return false
        }

        if(nisInput.text.toString() == "") {
            nisInput.error = "This is required field"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString()).matches()) {
            emailInput.error = "Check email format"
            return false
        }

        if (passwordInput.text.toString() == "") {
            passwordInput.error = "This is required field"
            return false
        }

        if (passwordInput.text.toString() != confirmPasswordInput.text.toString()) {
            confirmPasswordInput.error = "Password do not match"
            return false
        }

        if (confirmPasswordInput.text.toString() == "") {
            confirmPasswordInput.error = "This is required field"
            return false
        }
        return true
    }
}