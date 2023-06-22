package com.example.rinenggaapp.view.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentQuizBinding
import com.example.rinenggaapp.model.Module
import com.example.rinenggaapp.model.Question
import com.example.rinenggaapp.viewmodel.QuestionViewModel
import com.example.rinenggaapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import org.w3c.dom.Text


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
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

        answersTv = arrayListOf(option1, option2,option3,option4)

        val questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val moduleName = arguments?.getString("moduleName")

        questionViewModel.quizQuestion.observe(viewLifecycleOwner) {
            it.forEach { item ->
                questionList.add(item)
            }
            Log.d("quizQuestion", questionList.toString())

            updateQuestion()

            submitButton.setOnClickListener {
                if (!isAnswerChecked) {
                    val anyAnswerIsChecked = selectedAnswerIndex != -1
                    if (!anyAnswerIsChecked) {
                        Toast.makeText(requireContext(), "Please, select an alternative", Toast.LENGTH_SHORT).show()
                    } else {
                        val currentQuestion = questionList[currentQuestionIndex]
                        if (
                            selectedAnswerIndex == currentQuestion.correctAnswerIndex
                        ) {
                            answerView(answersTv!![selectedAnswerIndex], R.color.colorSuccess)
                            totalScore++
                        } else {
                            answerView(answersTv!![selectedAnswerIndex], R.color.colorError)
                            answerView(answersTv!![currentQuestion.correctAnswerIndex], R.color.colorSuccess)
                        }

                        isAnswerChecked = true
                        submitButton?.text = if (currentQuestionIndex == questionList.size - 1) "FINISH" else "GO TO NEXT QUESTION"
                        selectedAnswerIndex = -1
                    }
                } else {
                    if (currentQuestionIndex < questionList.size - 1) {
                        currentQuestionIndex++
                        updateQuestion()
                    } else {
                        val bundle = Bundle()
                        bundle.putInt("quizScore", totalScore)
                        bundle.putInt("totalQuestion", questionList.size)
                        Navigation.findNavController(requireView()).navigate(R.id.action_quizFragment_to_quizResultFragment, bundle)
                    }

                    isAnswerChecked = false
                }
            }

            answersTv?.let {
                for (optionIndex in it.indices) {
                    it[optionIndex].let {
                        it.setOnClickListener{
                            if (!isAnswerChecked) {
                                selectedAlternativeView(it as TextView, optionIndex)
                            }
                        }
                    }
                }
            }
        }
    }



    private fun updateQuestion() {

    // Render Question Text
    questionText?.text = questionList[currentQuestionIndex].questionText
    // Render Question Image
        progressBar?.progress = currentQuestionIndex + 1
    // Text of progress bar
    progressBarText?.text = "${currentQuestionIndex + 1}/${questionList.size}"

    for (answerIndex in questionList[currentQuestionIndex].answers!!.indices) {
        answersTv!![answerIndex].text = questionList[currentQuestionIndex].answers!![answerIndex]
    }

    submitButton?.text = if (currentQuestionIndex == questionList.size - 1) "FINISH" else "SUBMIT"
}

    @SuppressLint("ResourceAsColor")
    private fun selectedAlternativeView(option: TextView, index: Int) {
        selectedAnswerIndex = index

        option.setTextColor(R.color.colorAccent)
        option.setTypeface(option.typeface, Typeface.BOLD)
        option.background = ContextCompat.getDrawable(
            requireContext(),
            R.color.primary_40
        )

    }

    @SuppressLint("ResourceAsColor")
    private fun answerView(view: TextView, drawableId: Int) {
        view.background = ContextCompat.getDrawable(
            requireContext(),
            drawableId
        )
        answersTv!![selectedAnswerIndex].setTextColor(R.color.colorSuccess)

    }
}


