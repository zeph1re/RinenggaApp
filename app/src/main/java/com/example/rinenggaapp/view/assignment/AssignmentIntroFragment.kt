package com.example.rinenggaapp.view.assignment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rinenggaapp.databinding.FragmentAssignmentIntroBinding


class AssignmentIntroFragment : Fragment() {

    private var _binding: FragmentAssignmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentAssignmentIntroBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classEditText = binding.inputClass
        val nextButton = binding.nextButton

        val alertDialog = AlertDialog.Builder(requireContext())

        nextButton.setOnClickListener {
            if (classEditText.text.toString().isNotEmpty()) {
                alertDialog.setTitle("Mulai Ujian")
                    .setMessage("Jika sudah siap, silahkan klik tombol mulai ")
                    .setCancelable(true)
                    .setPositiveButton("Mulai") { dialogInterface, it ->
                        val inputClass = classEditText.text.toString()
                        val intent = Intent(requireActivity(), AssignmentActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("inputClass", inputClass)
                        intent.putExtra("className", inputClass)
                        startActivity(intent)
                    }.setNegativeButton("Batal"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }.show()
            } else{
                Toast.makeText(requireActivity(), "Silahkan isi kelas Anda", Toast.LENGTH_SHORT).show()
            }

        }
    }




}