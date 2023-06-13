package com.example.rinenggaapp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.model.Module
import com.example.rinenggaapp.repository.ModuleRepository

class HomeViewModel : ViewModel() {

    private val moduleRepository = ModuleRepository.getInstance()
    val listModule = moduleRepository.listModuleLiveData

    suspend fun getAllModule() = moduleRepository.getAllModule()






}