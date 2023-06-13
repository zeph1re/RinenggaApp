package com.example.rinenggaapp.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.Module
import com.google.firebase.firestore.FirebaseFirestore

class ModuleRepository {

    var db = FirebaseFirestore.getInstance()
    private val listModule = MutableLiveData<List<Module>>()
    val listModuleLiveData : LiveData<List<Module>> = listModule


    companion object {
        @Volatile
        private var instance: ModuleRepository? = null
        fun getInstance(): ModuleRepository{
            return instance ?: synchronized(this) {
                if (instance == null) {
                    instance = ModuleRepository()
                }
                return instance as ModuleRepository
            }
        }
    }

    fun getAllModule() {
        db.collection("module")
            .addSnapshotListener{ value, _ ->
                val listModuleResult: MutableList<Module> = mutableListOf()
                if(!value!!.isEmpty){
                    value.forEach { item ->
                        val module = item.toObject(Module::class.java)
                        listModuleResult += module
                    }
                }
                listModule.postValue(listModuleResult)
            }
    }
}