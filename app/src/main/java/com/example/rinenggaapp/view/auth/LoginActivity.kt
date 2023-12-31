package com.example.rinenggaapp.view.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.ActivityLoginBinding
import com.example.rinenggaapp.model.UserLogin
import com.example.rinenggaapp.view.MainActivity
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val loginViewModel : UserViewModel by viewModels()

    private lateinit var emailInput : EditText
    private lateinit var passwordInput : EditText
    private var passwordVisible = false


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.loginButton
        val registerNav = binding.registerNav
        emailInput = binding.emailInputLogin
        passwordInput = binding.passwordInputLogin


        registerNav.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        loginButton.setOnClickListener {
           if (checkFields()){
               processLogin()
           }
        }

        passwordInput.setOnTouchListener { view, motionEvent ->
            val right = 2
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= passwordInput.right - passwordInput.compoundDrawables[right].bounds.width()) {
                    val selection = passwordInput.selectionEnd
                    if (passwordVisible) {
                        passwordInput.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24, 0)
                        passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
                        passwordVisible = false
                    } else {
                        passwordInput.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24, 0)
                        passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        passwordVisible = true
                    }
                    passwordInput.setSelection(selection)
                    true
                }
            }
            false
        }
    }

    private fun processLogin() {
        val email = binding.emailInputLogin.text.toString()
        val password = binding.passwordInputLogin.text.toString()
        
        if (email.isEmpty() && password.isEmpty()){
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

    private fun checkFields() : Boolean {
        if(emailInput.text.toString() == "") {
            emailInput.error = "This is required field"
            return false
        }

        if (passwordInput.text.toString() == "") {
            passwordInput.error = "This is required field"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.text.toString()).matches()) {
            emailInput.error = "Check email format"
            return false
        }

        if (passwordInput.text.toString().trim().length < 8) {
            passwordInput.error = "Password must be 8 or more character"
            return false
        }

        return true
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity()
    }
}