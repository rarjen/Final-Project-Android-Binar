package com.example.finalprojectbinar.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.RvCardCoursesBinding
import com.example.finalprojectbinar.model.DataAllCourses

class KursusAdapter(private val onItemClickListener: OnItemClickListener? = null):  RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    private val diffCallback = object: DiffUtil.ItemCallback<DataAllCourses>(){
        override fun areItemsTheSame(oldItem: DataAllCourses, newItem: DataAllCourses): Boolean =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: DataAllCourses, newItem: DataAllCourses): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitCoursesResponse(value: List<DataAllCourses>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvCardCoursesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoursesHolder(binding)
    }
    override fun getItemCount(): Int = differ.currentList.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val linearHolder = holder as CoursesHolder
        val data = differ.currentList[position]
        data.let {
            linearHolder.onBind(data)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }

    }

    class CoursesHolder(val binding: RvCardCoursesBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(data: DataAllCourses){
            val (author, category, classCode, id, gambar, ispremium, level, nama, harga, rating, totalmenit, totalmodul) = data
            Glide.with(binding.ivCardImage).load(gambar).into(binding.ivCardImage)
            binding.tvCardTitleCourse.text = nama
            binding.tvCardAuthorCourse.text = author
            binding.tvCardLevel.text = "$level Level"
            binding.tvCardRate.text = rating.toString()
            binding.tvCardTotalModul.text = totalmodul.toString()
            binding.tvCardTotalTime.text = totalmenit.toString()
            binding.tvCardCategory.text = category
            if (ispremium){
                binding.statusLayout.background = ContextCompat.getDrawable(binding.root.context,  R.drawable.rounded_background)
                binding.tvStatus.text = "Premium"
            }else{
                binding.iconStatus.isVisible = false
                binding.statusLayout.background = ContextCompat.getDrawable(binding.root.context,  R.drawable.rounded_background_free)
                binding.tvStatus.text = "Mulai Kelas"
            }
        }
    }

}
interface OnItemClickCallback {
    fun onItemClicked(data: DataAllCourses)
}
