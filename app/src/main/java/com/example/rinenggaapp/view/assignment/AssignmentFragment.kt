package com.example.rinenggaapp.view.assignment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentAssignmentBinding
import com.example.rinenggaapp.model.Question
import com.example.rinenggaapp.viewmodel.QuestionViewModel
import com.example.rinenggaapp.viewmodel.UserViewModel
import java.util.Timer
import java.util.TimerTask
import kotlin.properties.Delegates


class AssignmentFragment : Fragment() {

    private var _binding: FragmentAssignmentBinding? = null
    private val binding get() = _binding!!

    private var selectedAnswerIndex : Int = -1
    private val questionList = ArrayList<Question>()
    private var answersTv = ArrayList<Button>()
    private var currentQuestionIndex = 0
    private var isAnswerChecked = false
    private var totalScore = 0

    private lateinit var questionText : TextView
    private lateinit var option1 : Button
    private lateinit var option2 : Button
    private lateinit var option3 : Button
    private lateinit var option4 : Button
    private lateinit var submitButton : Button
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarText : TextView
    private lateinit var assignmentTimer : ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAssignmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionText = binding.tvQuestion
        option1 = binding.optionOne
        option2 = binding.optionTwo
        option3 = binding.optionThree
        option4 = binding.optionFour
        submitButton = binding.btnSubmit
        progressBar = binding.progressBar
        progressBarText = binding.tvProgress

//       assignmentTimer = binding.assignmentTimer

        answersTv = arrayListOf(option1, option2,option3,option4)

        val questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        questionViewModel.allQuestion.observe(viewLifecycleOwner) {
            it.forEach { item ->
                questionList.add(item)
            }
            Log.d("quizQuestion", questionList.toString())

            updateQuestion()

            submitButton.setOnClickListener {
                if (!isAnswerChecked) {
                    val anyAnswerIsChecked = selectedAnswerIndex != -1
                    if (!anyAnswerIsChecked) {
                        Toast.makeText(requireContext(), "Please, select an answers", Toast.LENGTH_SHORT).show()
                    } else {
                        val currentQuestion = questionList[currentQuestionIndex]
                        if ( selectedAnswerIndex == currentQuestion.correctAnswerIndex ) {
                            correctAnswerView(answersTv[selectedAnswerIndex])
                            totalScore++
                            Log.d("correctAnswer",
                                currentQuestion.correctAnswerIndex.toString()
                            )
                            Log.d("totalScore",
                                totalScore.toString()
                            )
                        } else {
                            incorrectAnswerView(answersTv[selectedAnswerIndex])
                            correctAnswerView(answersTv[currentQuestion.correctAnswerIndex])
                        }

                        isAnswerChecked = true
                        submitButton.text = if (currentQuestionIndex == questionList.size-1) "FINISH" else "GO TO NEXT QUESTION"
                        selectedAnswerIndex = -1
                    }
                } else {
                    if (currentQuestionIndex < questionList.size - 1) {
                        currentQuestionIndex++
                        updateQuestion()
                    } else {
                        val bundle = Bundle()
                        bundle.putInt("assignmentScore", totalScore)
                        bundle.putInt("totalQuestion", questionList.size)
                        Navigation.findNavController(requireView()).navigate(R.id.action_assignmentFragment_to_assignmentResultFragment, bundle)
                    }

                    isAnswerChecked = false
                }
            }

            answersTv.let {
                for (optionIndex in it.indices) {
                    it[optionIndex].let {
                        it.setOnClickListener{
                            if (!isAnswerChecked) {
                                selectedAnswerView(it as Button, optionIndex)
                            }
                        }
                    }
                }
            }

//            timerAssignment()

            Log.d("score", totalScore.toString())
        }
    }

//    private fun timerAssignment() {
//        var count = 0
//        val timer = Timer()
//
//        timer.schedule(object : TimerTask() {
//            override fun run() {
//                count++
//                assignmentTimer.progress = count
//                if (count > 100) {
//                    timer.cancel()
//                    updateQuestion()
//                }
//            }
//        }, 0,100
//        )
//    }

    private fun updateQuestion() {
        defaultAnswersView()
        questionText.text = questionList[currentQuestionIndex].questionText
        progressBar.progress = currentQuestionIndex + 1
        progressBar.max = questionList.size
        progressBarText.text = "${currentQuestionIndex + 1}/${questionList.size}"
        for (answerIndex in questionList[currentQuestionIndex].answers!!.indices) {
            answersTv[answerIndex].text = questionList[currentQuestionIndex].answers!![answerIndex]
        }
        submitButton.text = if (currentQuestionIndex == questionList.size ) "FINISH" else "SUBMIT"
    }

    private fun defaultAnswersView() {
        for (answerTv in answersTv) {
            answerTv.typeface = Typeface.DEFAULT
            answerTv.textSize = 14.0F
            answerTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80))
            answerTv.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun selectedAnswerView(option: TextView, index: Int) {
        defaultAnswersView()
        selectedAnswerIndex = index
        option.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_40))
        option.setTypeface(option.typeface, Typeface.BOLD)
        option.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
    }

    private fun correctAnswerView(view: Button) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorSuccess))
        answersTv!![selectedAnswerIndex].setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun incorrectAnswerView(view : Button) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorError))
        answersTv!![selectedAnswerIndex].setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

}


