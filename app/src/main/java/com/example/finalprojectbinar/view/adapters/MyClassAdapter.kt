package com.example.finalprojectbinar.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.databinding.RvCardProgressBinding
import com.example.finalprojectbinar.model.DataMyClass

class MyClassAdapter(
    private val onButtonClick: (String) -> Unit
): RecyclerView.Adapter<MyClassAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<DataMyClass>() {
        override fun areItemsTheSame(
            oldItem: DataMyClass,
            newItem: DataMyClass
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: DataMyClass,
            newItem: DataMyClass
        ): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitMyClass(value: List<DataMyClass>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClassAdapter.ViewHolder {
        val infalter = LayoutInflater.from(parent.context)
        return  ViewHolder(RvCardProgressBinding.inflate(infalter, parent, false))
    }

    override fun onBindViewHolder(holder: MyClassAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private var binding: RvCardProgressBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.card.setOnClickListener{
                val courseId = differ.currentList[adapterPosition].id
                onButtonClick(courseId!!)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: DataMyClass){
            binding.apply {
                tvCardCategory.text = data.category.toString()
                tvCardRate.text = data.rating.toString()
                tvCardTitleCourse.text = data.name.toString()
                tvCardAuthorCourse.text= data.author.toString()
                tvCardLevel.text = "${data.level} Level"
                tvCardTotalModul.text = "${data.totalModule} Modul"
                tvCardTotalTime.text = "${data.totalMinute} Menit"
                tvIndicatorPercentage.text = "${data.progressBar}% complete"
                progressLearning.progress = data.progressBar!!
                Glide.with(this.courseCover)
                    .load(data.image)
                    .fitCenter()
                    .into(courseCover)

            }
        }
    }
}