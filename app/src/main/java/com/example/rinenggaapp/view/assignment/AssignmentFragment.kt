package com.example.rinenggaapp.view.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.rinenggaapp.databinding.FragmentAssignmentBinding
import com.example.rinenggaapp.viewmodel.QuestionViewModel
import kotlin.properties.Delegates


class AssignmentFragment : Fragment() {

    private var _binding: FragmentAssignmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var correctAnswer = 0
    private var notAnswer = 0
    private var wrongAnswer = 0

    private var totalQuestion by Delegates.notNull<Long>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val assignmentViewModel =
            ViewModelProvider(this)[QuestionViewModel::class.java]

        _binding = FragmentAssignmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val option1Btn = binding.optionOne
        val option2Btn = binding.optionTwo
        val option3Btn = binding.optionThree
        val option4Btn = binding.optionFour

        val questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]




        return root

    }


}