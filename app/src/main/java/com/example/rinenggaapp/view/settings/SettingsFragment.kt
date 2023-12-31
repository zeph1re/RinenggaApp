package com.example.rinenggaapp.view.settings

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.rinenggaapp.databinding.FragmentSettingsBinding
import com.example.rinenggaapp.view.auth.LoginActivity
import com.example.rinenggaapp.view.profile.ChangePasswordActivity
import com.example.rinenggaapp.view.profile.EditProfileActivity
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val settingViewModel =
            ViewModelProvider(this)[UserViewModel::class.java]


        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val name = binding.profileName
        val nis = binding.profileNis


        sharedPreferences = requireActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE)

        settingViewModel.currentUserProfile.observe(viewLifecycleOwner){
            if (it != null) {
                name.text = it.name
                nis.text = it.nis

            }
        }

        binding.editProfile.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
        }

        binding.changePassword.setOnClickListener {
            startActivity(Intent(requireActivity(), ChangePasswordActivity::class.java))
        }

        binding.logout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Logout")
                .setMessage("Jika anda logout, data quiz anda akan hilang")
                .setCancelable(true)
                .setPositiveButton("Ya") { _, _ ->
                    lifecycleScope.launch(Dispatchers.IO){
                        settingViewModel.logout()
                    }
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                }.setNegativeButton("Batal"){ dialogInterface, _ ->
                    dialogInterface.cancel()
                }.show()
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}