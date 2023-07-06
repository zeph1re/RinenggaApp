package com.example.rinenggaapp.view.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.R
import com.example.rinenggaapp.view.MainActivity
import com.example.rinenggaapp.databinding.ActivityChangePasswordBinding
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private var passwordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val oldPasswordInput = binding.oldPasswordInput
        val newPasswordInput = binding.newPasswordInput
        val confirmNewPasswordInput = binding.newConfirmPasswordInput
        val changePasswordButton = binding.changePasswordButton

        passwordVisibilityToggle(oldPasswordInput)
        passwordVisibilityToggle(newPasswordInput)
        passwordVisibilityToggle(confirmNewPasswordInput)

        changePasswordButton.setOnClickListener {
            val oldPassword = oldPasswordInput.text.toString()
            val newPassword = newPasswordInput.text.toString()
            val confirmNewPassword = confirmNewPasswordInput.text.toString()

            if(oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmNewPassword.isNotEmpty()) {
                if (newPassword == confirmNewPassword) {
                    lifecycleScope.launch {
                        userViewModel.changePassword(oldPassword, newPassword)
                    }

                    userViewModel.changePasswordStatus.observe(this){
                        when (it) {
                            "UPDATED" -> {
                                Toast.makeText(this, "Berhasil Ubah Password", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                            "OLD PASSWORD WRONG" -> {
                                Toast.makeText(this, "Password Lama tidak sesuai", Toast.LENGTH_SHORT).show()
                            }
                            "FAILED" -> {
                                Toast.makeText(this, "Gagal Ubah Password", Toast.LENGTH_SHORT).show()

                            }


                        }
                    }

                } else {
                    Toast.makeText(this, "Password baru dan Confirm Password harus sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Tidak boleh ada fields yang kosong", Toast.LENGTH_SHORT).show()

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun passwordVisibilityToggle(editView : EditText) {
        editView.setOnTouchListener { view, motionEvent ->
            val right = 2
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= editView.right - editView.compoundDrawables[right].bounds.width()) {
                    val selection = editView.selectionEnd
                    if (passwordVisible) {
                        editView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24, 0)
                        editView.transformationMethod = PasswordTransformationMethod.getInstance()
                        passwordVisible = false
                    } else {
                        editView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24, 0)
                        editView.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        passwordVisible = true
                    }
                    editView.setSelection(selection)
                    true
                }
            }
            false
        }
    }
}