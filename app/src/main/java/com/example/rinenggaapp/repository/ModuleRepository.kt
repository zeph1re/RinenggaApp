package com.example.rinenggaapp.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rinenggaapp.model.Module
import com.google.firebase.firestore.FirebaseFirestore

class ModuleRepository {

    private var db = FirebaseFirestore.getInstance()
    private val listModule = MutableLiveData<List<Module>>()
    val listModuleLiveData : LiveData<List<Module>> = listModule

    private val moduleData = MutableLiveData<List<Module>>()
    val moduleDataLiveData : LiveData<List<Module>> = moduleData


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

    suspend fun getAllModule() {
        val moduleCollection = db.collection("module")
        moduleCollection.addSnapshotListener{ value, _ ->
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

    fun getOneModuleData (moduleName : String) {
        val dataModule = db.collection("module")
        dataModule.whereEqualTo("name", moduleName)
            .addSnapshotListener{ value, _ ->
            val moduleDataResult: MutableList<Module> = mutableListOf()
            if(!value!!.isEmpty){
                value.forEach { item ->
                    val module = item.toObject(Module::class.java)
                    moduleDataResult += module
                }
            }
            moduleData.postValue(moduleDataResult)
        }
    }
}



