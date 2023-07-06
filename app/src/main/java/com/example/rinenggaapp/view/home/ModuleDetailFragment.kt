package com.example.rinenggaapp.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentModuleDetailBinding
import com.example.rinenggaapp.viewmodel.ModuleViewModel
import kotlinx.coroutines.launch


class ModuleDetailFragment : Fragment() {

    private var _binding: FragmentModuleDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentModuleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moduleDetail = (activity as HomeDetailActivity).getModuleDetail()
        Log.d("moduleName" , moduleDetail?.name.toString())

        val moduleViewModel = ViewModelProvider(this)[ModuleViewModel::class.java]

        val moduleTitle = binding.moduleTitle
        val moduleDescription = binding.moduleDescription
        val moduleExample1 = binding.moduleExample1
        val tuladhaText = binding.tuladhaText

        if (moduleDetail!!.name == "Basa Rinengga") {
            tuladhaText.isEnabled = false
        }

        lifecycleScope.launch {
            moduleViewModel.getModuleByName(moduleDetail!!.name)
        }

        moduleViewModel.moduleData.observe(viewLifecycleOwner){

            moduleTitle.text = moduleDetail!!.name
            moduleDescription.text = moduleDetail.detailModule?.definition
            moduleExample1.text = moduleDetail.detailModule?.example!!.joinToString( separator = "\n")
        }

        val finishButton = binding.finishButton
        finishButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("moduleName", moduleDetail?.name)
            bundle.putString("moduleImage", moduleDetail?.imageUrl)
            Navigation.findNavController(requireView()).navigate(R.id.action_moduleDetailFragment_to_quizDetailFragment, bundle)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}