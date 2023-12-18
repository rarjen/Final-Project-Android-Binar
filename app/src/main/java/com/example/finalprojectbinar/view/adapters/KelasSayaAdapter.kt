package com.example.finalprojectbinar.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.databinding.RvCardProgressBinding
import com.example.finalprojectbinar.view.model_dummy.DataMyClass

class KelasSayaAdapter(private val dataList: ArrayList<DataMyClass>) : RecyclerView.Adapter<KelasSayaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KelasSayaAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(RvCardProgressBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: KelasSayaAdapter.ViewHolder, position: Int) {
        val(image, level, author, title, category, rating, modul, menit) = dataList[position]
        holder.rvImage.setImageResource(image)
        holder.rvTitle.text = title
        holder.rvCategory.text = category
        holder.rvLevel.text = level
        holder.rvRating.text = rating
        holder.rvModul.text = modul
        holder.rvMenit.text = menit
        holder.rvAuthor.text = author
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(private var binding: RvCardProgressBinding): RecyclerView.ViewHolder(binding.root){

        val rvImage = binding.courseCover
        val rvAuthor = binding.tvCardAuthorCourse
        val rvTitle = binding.tvCardTitleCourse
        val rvCategory = binding.tvCardCategory
        val rvLevel = binding.tvCardLevel
        val rvRating = binding.tvCardRate
        val rvModul = binding.tvCardTotalModul
        val rvMenit = binding.tvCardTotalTime

    }
}