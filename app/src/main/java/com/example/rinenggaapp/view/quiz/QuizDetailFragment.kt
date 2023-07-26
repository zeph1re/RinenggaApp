package com.example.rinenggaapp.view.quiz

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentQuizDetailBinding
import com.example.rinenggaapp.viewmodel.QuestionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizDetailFragment : Fragment() {

    private var _binding : FragmentQuizDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuizDetailBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val moduleName = arguments?.getString("moduleName")
        val moduleImage = arguments?.getString("moduleImage")

        val quizViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        val title = binding.detailFragmentTitle
        val totalQuestion = binding.detailFragmentQuestions
        val startQuizButton = binding.startQuizBtn
        val imageModule = binding.detailFragmentImage
        val progressBar = binding.detailProgressBar

        lifecycleScope.launch(Dispatchers.IO) {
            quizViewModel.getQuestionbyModuleName(moduleName!!)
        }

        quizViewModel.quizQuestion.observe(viewLifecycleOwner){
            title.text = moduleName
            Glide.with(requireContext()).load(moduleImage).into(imageModule)
            Handler().postDelayed( {
                progressBar.visibility = View.GONE
            }, 2000)
            totalQuestion.text = it.size.toString()
        }


        startQuizButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("moduleName", moduleName)
            Navigation.findNavController(requireView()).navigate(R.id.action_quizDetailFragment_to_quizFragment, bundle)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}