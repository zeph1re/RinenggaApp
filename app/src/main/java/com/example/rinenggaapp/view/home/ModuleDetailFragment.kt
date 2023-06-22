package com.example.rinenggaapp.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentHomeBinding
import com.example.rinenggaapp.databinding.FragmentModuleDetailBinding
import com.example.rinenggaapp.model.Module
import java.util.Arrays
import java.util.Arrays.*


class ModuleDetailFragment : Fragment() {

    private var _binding: FragmentModuleDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentModuleDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moduleDetail = (activity as HomeDetailActivity).getModuleDetail()
        Log.d("module" , moduleDetail.toString())

        val moduleTitle = binding.moduleTitle
        val moduleDescription = binding.moduleDescription
        val moduleExample1 = binding.moduleExample1
        val moduleExample2 = binding.moduleExample2
        val moduleExample3 = binding.moduleExample3
        moduleTitle.text = moduleDetail?.name.toString()
        moduleDescription.text = moduleDetail?.detailModule?.definition.toString()


        for (example in moduleDetail?.detailModule?.example!!) {
            Log.d("example", example)
            moduleExample1.text = example
        }

//        moduleExample1.text = moduleDetail?.detailModule?.example!![0]
//        moduleExample2.text = moduleDetail?.detailModule?.example!![1]
//        if (moduleDetail.detailModule.example[2].isEmpty() ) {
//            moduleExample3.text = moduleDetail?.detailModule?.example!![2]
//        } else {
//            moduleExample3.text = ""
//        }


        val finishButton = binding.finishButton
        finishButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("moduleName", moduleDetail.name)
            bundle.putString("moduleImage", moduleDetail.imageUrl)
            Navigation.findNavController(requireView()).navigate(R.id.action_moduleDetailFragment_to_quizDetailFragment, bundle)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}