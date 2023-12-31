package com.example.rinenggaapp.view.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
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
import com.example.rinenggaapp.databinding.FragmentQuizBinding
import com.example.rinenggaapp.model.Question
import com.example.rinenggaapp.viewmodel.QuestionViewModel


class QuizFragment : Fragment() {

    private var _binding : FragmentQuizBinding? = null
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
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
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

        assignmentTimer = binding.quizTimer

        answersTv = arrayListOf(option1, option2,option3,option4)

        val questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]


        questionViewModel.quizQuestion.observe(viewLifecycleOwner) { questionList ->
            questionList.forEach { item ->
                this.questionList.add(item)
            }

            updateQuestion()

            submitButton.setOnClickListener {
                if (!isAnswerChecked) {
                    val anyAnswerIsChecked = selectedAnswerIndex != -1
                    if (!anyAnswerIsChecked) {
                        Toast.makeText(requireContext(), "Please, select an alternative", Toast.LENGTH_SHORT).show()
                    } else {
                        val currentQuestion = this.questionList[currentQuestionIndex]
                        if ( selectedAnswerIndex == currentQuestion.correctAnswerIndex ) {
                            correctAnswerView(answersTv[selectedAnswerIndex])
                            totalScore++
                        } else {
                            incorrectAnswerView(answersTv[selectedAnswerIndex])
                            correctAnswerView(answersTv[currentQuestion.correctAnswerIndex])
                        }

                        isAnswerChecked = true
                        submitButton.text = if (currentQuestionIndex == this.questionList.size-1) "FINISH" else "GO TO NEXT QUESTION"
                        selectedAnswerIndex = -1
                    }
                } else {
                    if (currentQuestionIndex < this.questionList.size - 1) {
                        currentQuestionIndex++
                        updateQuestion()
                    } else {
                        val bundle = Bundle()
                        bundle.putInt("quizScore", totalScore)
                        bundle.putInt("totalQuestion", this.questionList.size)
                        Navigation.findNavController(requireView()).navigate(R.id.action_quizFragment_to_quizResultFragment, bundle)
                    }

                    isAnswerChecked = false
                }
            }

            answersTv.let { buttonList ->
                for (optionIndex in buttonList.indices) {
                    buttonList[optionIndex].let { button ->
                        button.setOnClickListener{
                            if (!isAnswerChecked) {
                                selectedAnswerView(it as Button, optionIndex)
                            }
                        }
                    }
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
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
            answerTv.textSize = 17.0F
            answerTv.setTextColor(ContextCompat.getColor(requireContext(),R.color.primary_80))
            answerTv.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.neutral_20))
        }
    }

    private fun selectedAnswerView(option: TextView, index: Int) {
        defaultAnswersView()
        selectedAnswerIndex = index
        option.setTypeface(option.typeface, Typeface.BOLD)
        option.setBackgroundColor(Color.LTGRAY)
    }

    private fun correctAnswerView(view: Button) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorSuccess))
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

    }

    private fun incorrectAnswerView(view : Button) {
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorError))
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }




}



