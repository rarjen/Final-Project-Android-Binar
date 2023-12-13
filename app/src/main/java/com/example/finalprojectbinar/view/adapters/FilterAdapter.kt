package com.example.finalprojectbinar.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.databinding.ItemFilterCheckboxBinding
import com.example.finalprojectbinar.databinding.ItemFilterHeaderBinding
import com.example.finalprojectbinar.model.DataFilter
import com.example.finalprojectbinar.view.model_dummy.ListFilter
import java.util.ArrayList

class FilterAdapter(private val list: List<ListFilter>, private val dataFilter: ArrayList<DataFilter>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val filter = ArrayList<DataFilter>()
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
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
    interface OnItemClickCallback {
        fun onItemClicked(data: ListFilter.CheckboxItem, cbFilter: CheckBox)
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
            is ListFilter.CheckboxItem -> {
                val checkboxHolder = holder as CheckboxViewHolder
                checkboxHolder.bind(item)
                checkboxHolder.binding.cbFilter.setOnClickListener{
                    onItemClickCallback.onItemClicked(item.copy(position), checkboxHolder.binding.cbFilter)
                }
            }
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
            var isChecked = false
            if (dataFilter != null) {
                for (item in dataFilter){
                    when(item){
                        is DataFilter.Category ->{
                            if (checkbox.id == item.categoryId?.toInt()){
                                isChecked = true
                            }
                        }
                        is DataFilter.Level ->{
                            if (checkbox.tag.equals(item.level)){
                                isChecked = true
                            }
                        }
                    }
                }
            }

            // Setel status isChecked pada checkbox
            binding.cbFilter.isChecked = isChecked
        }
    }
}