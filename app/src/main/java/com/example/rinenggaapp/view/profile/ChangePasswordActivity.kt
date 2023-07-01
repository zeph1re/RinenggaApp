package com.example.rinenggaapp.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.view.MainActivity
import com.example.rinenggaapp.databinding.ActivityChangePasswordBinding
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val oldPasswordInput = binding.oldPasswordInput
        val newPasswordInput = binding.newPasswordInput
        val confirmNewPasswordInput = binding.newConfirmPasswordInput
        val changePasswordButton = binding.changePasswordButton

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
}