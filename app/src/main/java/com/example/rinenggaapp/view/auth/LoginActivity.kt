package com.example.rinenggaapp.view.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.ActivityLoginBinding
import com.example.rinenggaapp.databinding.ActivityMainBinding
import com.example.rinenggaapp.datastore.SharedPreferences
import com.example.rinenggaapp.model.UserLogin
import com.example.rinenggaapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val loginViewModel : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val emailPrefs = sharedPreferences.getString("EMAIL", "")
        val passwordPrefs = sharedPreferences.getString("PASSWORD", "")

        if (emailPrefs!!.isNotEmpty() && passwordPrefs!!.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
        }


        val loginButton = binding.loginButton
        val registerNav = binding.registerNav


        registerNav.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        loginButton.setOnClickListener {
            processLogin()

            val editor = sharedPreferences.edit()
            editor.putString("EMAIL", binding.emailInputLogin.text.toString())
            editor.putString("PASSWORD", binding.passwordInputLogin.text.toString())
            editor.apply()
        }
    }

    fun processLogin() {
        val email = binding.emailInputLogin.text.toString()
        val password = binding.passwordInputLogin.text.toString()
        
        if (email.isNullOrEmpty() && password.isNullOrEmpty()){
            Toast.makeText(this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch {
                loginViewModel.loginAccount(UserLogin(email,password))
            }

            loginViewModel.loginStatus.observe(this) {
                when (it) {
                    "OK" -> {
                        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    "REGISTERED" -> Toast.makeText(this, "Email sudah terdaftar dan belum divirifikasi", Toast.LENGTH_SHORT).show()
                    else -> {
                        Toast.makeText(this, "Email belum Terdaftar", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }


    }
}