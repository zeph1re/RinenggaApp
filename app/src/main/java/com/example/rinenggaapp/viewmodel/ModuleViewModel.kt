package com.example.rinenggaapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.rinenggaapp.repository.ModuleRepository

class ModuleViewModel : ViewModel(){

    private val moduleRepository = ModuleRepository.getInstance()
    val listModule = moduleRepository.listModuleLiveData
    val moduleData = moduleRepository.moduleDataLiveData

    suspend fun getAllModule() = moduleRepository.getAllModule()
}