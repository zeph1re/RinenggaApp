package com.example.rinenggaapp.view.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rinenggaapp.R
import com.example.rinenggaapp.model.Module
import com.example.rinenggaapp.model.User

import com.example.rinenggaapp.view.adapter.ModuleAdapter
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {


    private lateinit var moduleArrayList  : ArrayList<Module>
    private lateinit var moduleRecyclerView: RecyclerView
    private lateinit var moduleAdapter : ModuleAdapter
    private val moduleViewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moduleRecyclerView = view.findViewById(R.id.module_recyclerView)
        initRecyclerView()
    }

    fun initRecyclerView() {
        moduleAdapter = ModuleAdapter()

        moduleRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        moduleRecyclerView.adapter = moduleAdapter

        lifecycleScope.launch(Dispatchers.IO){
            moduleViewModel.getAllModule()
        }

        moduleViewModel.listModule.observe(viewLifecycleOwner){
            moduleAdapter.setListModule(it)
            moduleAdapter.notifyDataSetChanged()
        }

    }
}