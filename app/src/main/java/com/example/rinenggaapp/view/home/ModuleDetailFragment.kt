package com.example.rinenggaapp.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentHomeBinding
import com.example.rinenggaapp.databinding.FragmentModuleDetailBinding
import com.example.rinenggaapp.model.Module


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
        moduleTitle.text = moduleDetail?.name.toString()
        moduleDescription.text = moduleDetail?.detailModule?.definition.toString()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}