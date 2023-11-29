package com.example.finalprojectbinar.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.RvCardCoursesBinding
import com.example.finalprojectbinar.view.model_dummy.DataKelas

class KursusAdapter(val dataKelas: ArrayList<DataKelas>):  RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvCardCoursesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoursesHolder(binding)
    }
    override fun getItemCount(): Int = dataKelas.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val linearHolder = holder as CoursesHolder
        linearHolder.onBind(dataKelas[position])

    }

    class CoursesHolder(val binding: RvCardCoursesBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(data: DataKelas){
            val (nama, gambar, author, harga, level, rating, totalmodul, totalmenit, ispremiun, classCode, category) = data
            Glide.with(binding.ivCardImage).load(gambar).into(binding.ivCardImage)
            binding.tvCardTitleCourse.text = nama
            binding.tvCardAuthorCourse.text = author
            binding.tvCardLevel.text = level
            binding.tvCardRate.text = rating.toString()
            binding.tvCardTotalModul.text = totalmodul.toString()
            binding.tvCardTotalTime.text = totalmenit.toString()
            binding.tvCardCategory.text = category
            if (ispremiun){
                binding.statusLayout.background = ContextCompat.getDrawable(binding.root.context,  R.drawable.rounded_background)
                binding.tvStatus.text = "Paid"
            }else{
                binding.iconStatus.isVisible = false
                binding.statusLayout.background = ContextCompat.getDrawable(binding.root.context,  R.drawable.rounded_background_free)
                binding.tvStatus.text = "Free"
            }
        }
    }
}