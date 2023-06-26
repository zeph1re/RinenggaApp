package com.example.rinenggaapp.view.assignment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentAssignmentDetailBinding
import com.example.rinenggaapp.view.home.HomeDetailActivity
import com.example.rinenggaapp.viewmodel.QuestionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AssignmentDetailFragment : Fragment() {

    private var _binding : FragmentAssignmentDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssignmentDetailBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val className = (activity as AssignmentActivity).getClassName()
        Log.d("className" , className.toString())

        val totalQuestion = binding.totalQuestionDetail
        val startAssignmentButton = binding.startAssignmentButton

        val questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        lifecycleScope.launch(Dispatchers.IO) {
            questionViewModel.getAllQuestion()
        }

        questionViewModel.allQuestion.observe(viewLifecycleOwner){
            Log.d("assignment question" , it.toString())
            totalQuestion.text = it.size.toString()
        }

        startAssignmentButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("className", className)
            Navigation.findNavController(requireView()).navigate(R.id.action_assignmentDetailFragment2_to_assignmentFragment, bundle)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}