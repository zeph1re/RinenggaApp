package com.example.rinenggaapp.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.ActivityMainBinding
import com.example.rinenggaapp.view.assignment.AssignmentIntroFragment
import com.example.rinenggaapp.view.auth.LoginActivity
import com.example.rinenggaapp.view.home.HomeFragment
import com.example.rinenggaapp.view.profile.EditProfileActivity
import com.example.rinenggaapp.view.settings.SettingsFragment
import com.example.rinenggaapp.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.currentUserProfile.observe(this) {
            if (it!!.id.isNullOrEmpty()) {
                Toast.makeText(this, "Silahkan Login terlebih dahulu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                if (it.no_hp.isNullOrEmpty()) {
                    Toast.makeText(this, "Silahkan lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, EditProfileActivity::class.java))
                }
            }
        }

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_assignment -> replaceFragment(AssignmentIntroFragment())
                R.id.navigation_settings -> replaceFragment(SettingsFragment())
                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment (fragment : Fragment){
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.container, fragment)
        fragmentManager.commit()
    }

    override fun onBackPressed() {

    }

}