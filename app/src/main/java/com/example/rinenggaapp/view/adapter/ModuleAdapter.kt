package com.example.rinenggaapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rinenggaapp.R
import com.example.rinenggaapp.model.Module
import com.example.rinenggaapp.model.User

class ModuleAdapter(private val onClick : (Module) -> Unit) : RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {

    private var moduleList: List<Module>? = null
    fun setListModule(list: List<Module>){
        this.moduleList = list
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val moduleTitle: TextView = itemView.findViewById(R.id.module_title)
        val moduleImage: ImageView = itemView.findViewById(R.id.module_image)
        val moduleCard: View = itemView.findViewById(R.id.moduleCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_module, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (moduleList == null) {
           return 0
        }
        return  moduleList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.moduleTitle.text = moduleList!![position].name

        this.let {
            Glide.with(holder.itemView.context).load(moduleList!![position].imageUrl).into(holder.moduleImage)
        }
        holder.moduleCard.setOnClickListener{
            onClick(moduleList!![position])
        }

    }


}