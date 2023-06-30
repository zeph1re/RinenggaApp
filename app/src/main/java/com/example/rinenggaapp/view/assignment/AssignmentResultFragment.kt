package com.example.rinenggaapp.view.assignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentAssignmentResultBinding
import com.example.rinenggaapp.databinding.FragmentQuizResultBinding
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class AssignmentResultFragment : Fragment() {


    private var _binding : FragmentAssignmentResultBinding? = null
    private val binding get() = _binding!!

    private var totalScore : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAssignmentResultBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val assignmentScore = arguments?.getInt("assignmentScore")
        val totalQuestion = arguments?.getInt("totalQuestion")

        val textResult = binding.resultNumber
        val questionAnswered = binding.questionAnswered



        if (assignmentScore != null) {
            totalScore = ((assignmentScore.toFloat()/ totalQuestion!!.toFloat()) * 100).roundToInt()
            Log.d("totalScoreResult", totalScore.toString())
        }

        lifecycleScope.launch {
            userViewModel.putAssignmentScore(totalScore)
        }

        textResult.text = totalScore.toString()
        questionAnswered.text = "Anda telah mengerjakan $totalQuestion"

        userViewModel.updateAssignmentScore.observe(viewLifecycleOwner) {
           if (it == "OK") {
                    Handler().postDelayed({
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                    }, 3000)
            }
        }
        return root
    }




}