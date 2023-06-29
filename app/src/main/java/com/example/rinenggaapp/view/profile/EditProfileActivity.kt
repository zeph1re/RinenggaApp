package com.example.rinenggaapp.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.ActivityEditProfileBinding
import com.example.rinenggaapp.databinding.ActivityMainBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val saveButton = binding.changeProfileButton
        val fullName = binding.fullNameInputEdit.text.toString()
        val nis = binding.nisInputEdit.text.toString()
        val email = binding.emailChange.text.toString()
        val phoneNumber = binding.noHpChange.text.toString()

        saveButton.setOnClickListener {
            if (fullName.isNotEmpty() && nis.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty()){

                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Harap isi dengan lengkap!!", Toast.LENGTH_SHORT).show()
            }
            
        }


    }
}