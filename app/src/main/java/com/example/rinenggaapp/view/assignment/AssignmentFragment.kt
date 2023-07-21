package com.example.rinenggaapp.view.assignment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentAssignmentBinding
import com.example.rinenggaapp.model.Question
import com.example.rinenggaapp.viewmodel.QuestionViewModel


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
        return binding.root

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

       assignmentTimer = binding.timer

        answersTv = arrayListOf(option1, option2,option3,option4)

        val questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]


        questionViewModel.allQuestion.observe(viewLifecycleOwner) { listQuestion ->
            listQuestion.forEach { item ->
                questionList.add(item)
            }
            Log.d("quizQuestion", questionList.toString())

            updateQuestion()
//            timerAssignment()

            submitButton.setOnClickListener {
                if (!isAnswerChecked) {
                    val anyAnswerIsChecked = selectedAnswerIndex != -1
                    if (!anyAnswerIsChecked) {
                        Toast.makeText(requireContext(), "Please, select an answers", Toast.LENGTH_SHORT).show()
                    } else {
                        val currentQuestion = questionList[currentQuestionIndex]
                        if ( selectedAnswerIndex == currentQuestion.correctAnswerIndex ) {
                            totalScore++
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

            answersTv.let { listButton ->
                for (optionIndex in listButton.indices) {
                    listButton[optionIndex].let { button ->
                        button.setOnClickListener{
                            if (!isAnswerChecked) {
                                selectedAnswerView(it as Button, optionIndex)
                            }
                        }
                    }
                }
            }



            Log.d("score", totalScore.toString())
        }
    }

    private fun timerAssignment() {
        object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                assignmentTimer.progress = (millisUntilFinished/1000).toInt()
            }

            override fun onFinish() {
                currentQuestionIndex++
                updateQuestion()
            }
        }.start()

    }

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
            answerTv.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.neutral_20))
        }
    }

    private fun selectedAnswerView(option: TextView, index: Int) {
        defaultAnswersView()
        selectedAnswerIndex = index
        option.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_40))
        option.setTypeface(option.typeface, Typeface.BOLD)
        option.setBackgroundColor(Color.LTGRAY)
    }

    private fun correctAnswerView(view: Button) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorSuccess))
        answersTv[selectedAnswerIndex].setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun incorrectAnswerView(view : Button) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorError))
        answersTv[selectedAnswerIndex].setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

}


