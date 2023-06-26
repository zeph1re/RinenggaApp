package com.example.rinenggaapp.view.quiz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentQuizDetailBinding
import com.example.rinenggaapp.databinding.FragmentQuizResultBinding
import com.example.rinenggaapp.view.starter_page.StarterPageActivity
import kotlin.math.roundToInt


class QuizResultFragment : Fragment() {

    private var _binding : FragmentQuizResultBinding? = null
    private val binding get() = _binding!!

    private var totalScore : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizResultBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val quizScore = arguments?.getInt("quizScore")
        val totalQuestion = arguments?.getInt("totalQuestion")

        Log.d("quizScore", quizScore.toString())
        Log.d("totalQuestion", totalQuestion.toString())

        if (quizScore != null) {
            totalScore = ((quizScore.toFloat()/ totalQuestion!!.toFloat()) * 100).roundToInt()
            Log.d("totalScoreResult", totalScore.toString())
        }





        val resultValue = binding.resultNumber
        val answeredValue = binding.correctAnswer
        val congratulationTv = binding.congratulationTv

        resultValue.text = totalScore.toString()
        answeredValue.text = "Anda telah menyelesaikan $totalQuestion Soal Quiz"

//        val userName = intent.getStringExtra(Constants.USER_NAME)
//        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
//        val score = intent.getIntExtra(Constants.SCORE, 0)
//
//        congratulationTv.text = "$userName mendapatkan nilai"
//        resultValue.text = "$score"
//        answeredValue.text = "Your score is $score of $totalQuestions"
//        btnRestart.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        Handler().postDelayed( { //This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
            // close this activity
        }, 10000)

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}