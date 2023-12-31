package com.example.rinenggaapp.view.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.view.MainActivity
import com.example.rinenggaapp.databinding.ActivityEditProfileBinding
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val saveButton = binding.changeProfileButton
        val fullNameInput = binding.fullNameInputEdit
        val nisInput = binding.nisInputEdit
        val emailInput = binding.emailChange
        val phoneNumberInput = binding.noHpChange

        var oldEmail = ""

        userViewModel.currentUserProfile.observe(this) {
            if (it != null) {
                fullNameInput.setText(it.name)
                nisInput.setText(it.nis)
                emailInput.setText(it.email)
                oldEmail = it.email
                phoneNumberInput.setText(it.no_hp)
            }
        }

        saveButton.setOnClickListener {
            val fullName = fullNameInput.text.toString()
            val nis = nisInput.text.toString()
            val email = emailInput.text.toString()
            val phoneNumber = phoneNumberInput.text.toString()


            if (fullName.isNotEmpty() && nis.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty()){

                lifecycleScope.launch {
                    userViewModel.checkEmailAlreadyRegistered(email)
                    userViewModel.editProfile(fullName, nis, email, phoneNumber)
                }

                userViewModel.checkEmailRegistered.observe(this) {check ->
                    when (check) {
                        "OK" -> {
                            userViewModel.updateProfileStatus.observe(this){ it ->
                                if (it == "OK"){
                                    Toast.makeText(this, "Berhasil Update Profile", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                }
                            }
                        }
                        "ALREADY REGISTERED" -> {
                            if (email == oldEmail) {
                                userViewModel.updateProfileStatus.observe(this) {
                                    if (it == "OK"){
                                        Toast.makeText(this, "Berhasil Update Profile", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Email sudah digunakan!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Harap isi dengan lengkap!!", Toast.LENGTH_SHORT).show()
            }
            
        }
    }

}