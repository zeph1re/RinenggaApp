package com.example.rinenggaapp.view.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentHomeBinding
import com.example.rinenggaapp.model.Module
import com.example.rinenggaapp.model.User

import com.example.rinenggaapp.view.adapter.ModuleAdapter
import com.example.rinenggaapp.viewmodel.ModuleViewModel
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var moduleAdapter : ModuleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeViewModel =
            ViewModelProvider(this)[ModuleViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val moduleRecyclerView = binding.moduleRecyclerView
        moduleAdapter = ModuleAdapter{ module ->
            val sendDetail = Intent(requireActivity(), HomeDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("MODULE", module)
            sendDetail.putExtras(bundle)
            Log.d("Clicked", module.toString())
            startActivity(sendDetail)
        }

        moduleRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        moduleRecyclerView.adapter = moduleAdapter

        lifecycleScope.launch(Dispatchers.IO){
            homeViewModel.getAllModule()
        }

        homeViewModel.listModule.observe(viewLifecycleOwner){
            moduleAdapter.setListModule(it)
            moduleAdapter.notifyDataSetChanged()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}