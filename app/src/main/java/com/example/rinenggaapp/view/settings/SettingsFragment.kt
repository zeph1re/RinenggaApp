package com.example.rinenggaapp.view.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.databinding.FragmentSettingsBinding
import com.example.rinenggaapp.view.auth.LoginActivity
import com.example.rinenggaapp.view.profile.ChangePasswordActivity
import com.example.rinenggaapp.view.profile.EditProfileActivity
import com.example.rinenggaapp.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val settingViewModel =
            ViewModelProvider(this)[AuthViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

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
                .setPositiveButton("Mulai") { dialogInterface, it ->
                    lifecycleScope.launch(Dispatchers.IO){
                        settingViewModel.logout()
                    }

                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                }.setNegativeButton("Batal"){dialogInterface, it ->
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