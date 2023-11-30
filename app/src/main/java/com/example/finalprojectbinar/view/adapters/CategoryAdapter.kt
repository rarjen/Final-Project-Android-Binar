package com.example.finalprojectbinar.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.databinding.KategoriBinding
import com.example.finalprojectbinar.model.DataCategories

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<DataCategories>() {
        override fun areItemsTheSame(
            oldItem: DataCategories,
            newItem: DataCategories
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: DataCategories,
            newItem: DataCategories
        ): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitCategoryMenuResponse(value: List<DataCategories>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(KategoriBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ViewHolder(private var binding: KategoriBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataCategories) {
            binding.apply {
                tvCategoryName.text = data.name
                Glide.with(this.shapeableimageviewKategori)
                    .load(data.image)
                    .fitCenter()
                    .into(binding.shapeableimageviewKategori)
            }
        }
    }

}