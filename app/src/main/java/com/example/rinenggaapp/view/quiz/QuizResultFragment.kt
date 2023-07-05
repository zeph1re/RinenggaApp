package com.example.rinenggaapp.view.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rinenggaapp.view.MainActivity
import com.example.rinenggaapp.databinding.FragmentQuizResultBinding
import kotlin.math.roundToInt


class QuizResultFragment : Fragment() {

    private var _binding : FragmentQuizResultBinding? = null
    private val binding get() = _binding!!

    private var totalScore : Int = 0


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizResultBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val quizScore = arguments?.getInt("quizScore")
        val totalQuestion = arguments?.getInt("totalQuestion")

        if (quizScore != null) {
            totalScore = ((quizScore.toFloat()/ totalQuestion!!.toFloat()) * 100).roundToInt()
        }

        val resultValue = binding.resultNumber
        val answeredValue = binding.questionAnswered

        resultValue.text = totalScore.toString()
        answeredValue.text = "Anda telah menyelesaikan $totalQuestion Soal Quiz"

        Handler().postDelayed( {
            val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
        }, 5000)

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}