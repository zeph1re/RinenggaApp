package com.example.rinenggaapp.view.assignment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentAssignmentIntroBinding
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch


class AssignmentIntroFragment : Fragment() {

    private var _binding: FragmentAssignmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentAssignmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val classDropDown = binding.inputClassDropdown
        val nextButton = binding.nextButton

        val classes = arrayOf("X1","X2","X3","X4","X5","X6","X7","X8","X9","X10" )
        var classInfo = ""

        val classAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, classes)
        classDropDown.adapter = classAdapter
        classDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                classInfo = classes[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Silahkan pilih kelas", Toast.LENGTH_SHORT).show()
            }
        }

        userViewModel.currentUserProfile.observe(viewLifecycleOwner){
            if (it!!.assignmentResult != null ) {
                nextButton.isEnabled = false
                classDropDown.isEnabled = false
                Toast.makeText(requireContext(), "Anda sudah melakukan Pawulang!! \n tidak dapat mengulang lagi", Toast.LENGTH_SHORT).show()
            } else {
                val alertDialog = AlertDialog.Builder(requireContext())

                nextButton.setOnClickListener {
                    if (classInfo.isNotEmpty()) {
                        alertDialog.setTitle("Mulai Ujian")
                            .setMessage("Cek kembali kelas anda, Pawulang hanya dapat digunakan sekali!!\n\nHarap hati-hati!!\n\nJika anda keluar dari aplikasi saat ujian berlangsung maka nilai anda langsung 0")
                            .setCancelable(true)
                            .setPositiveButton("Ya") { _, _ ->
                                val intent = Intent(requireActivity(), AssignmentActivity::class.java)
                                intent.putExtra("classInfo", classInfo)
                                startActivity(intent)
                            }.setNegativeButton("Batal"){ dialogInterface, _ ->
                                dialogInterface.cancel()
                            }.show()
                    } else{
                        Toast.makeText(requireActivity(), "Silahkan isi kelas Anda", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}