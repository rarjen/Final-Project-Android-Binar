package com.example.finalprojectbinar.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.databinding.ItemFilterCheckboxBinding
import com.example.finalprojectbinar.databinding.ItemFilterHeaderBinding
import com.example.finalprojectbinar.databinding.RvCardCoursesBinding
import com.example.finalprojectbinar.view.model_dummy.ListFilter

class FilterAdapter(private val list: List<ListFilter>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> {
                val binding = ItemFilterHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }
            1 -> {
                val binding = ItemFilterCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CheckboxViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position]){
            is ListFilter.HeaderItem -> 0
            is ListFilter.CheckboxItem -> 1
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = list[position]){
            is ListFilter.HeaderItem -> (holder as HeaderViewHolder).bind(item)
            is ListFilter.CheckboxItem -> (holder as CheckboxViewHolder).bind(item)
        }
    }

    inner class HeaderViewHolder(val binding: ItemFilterHeaderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(header: ListFilter.HeaderItem){
            binding.tvHeaderFilter.text = header.text
        }
    }
    inner class CheckboxViewHolder(val binding: ItemFilterCheckboxBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(checkbox: ListFilter.CheckboxItem){
            binding.cbFilter.text = checkbox.text
        }
    }
}