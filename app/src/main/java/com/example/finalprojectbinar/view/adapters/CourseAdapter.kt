package com.example.finalprojectbinar.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.databinding.CardRectangleCourseBinding
import com.example.finalprojectbinar.model.DataAllCourses

class CourseAdapter (
    private val onItemClick: OnItemClickListener? = null,
    private val onButtonClick: (String, Boolean) -> Unit
) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<DataAllCourses>() {
        override fun areItemsTheSame(
            oldItem: DataAllCourses,
            newItem: DataAllCourses
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: DataAllCourses,
            newItem: DataAllCourses
        ): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitCoursesResponse(value: List<DataAllCourses>) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(CardRectangleCourseBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    inner class ViewHolder(private var binding: CardRectangleCourseBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.button.setOnClickListener {
                val courseId = differ.currentList[adapterPosition].id
                val isPremium = differ.currentList[adapterPosition].isPremium
                onButtonClick(courseId, isPremium)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: DataAllCourses) {
            binding.apply {
                tvCardCategory.text = data.category
                tvCardTitleCourse.text = data.name
                tvCardAuthorCourse.text = data.author
                tvCardLevel.text = "${data.level} Level"
                tvCardRate.text = data.rating.toString()
                tvCardTotalModul.text = data.totalModule.toString()
                tvCardTotalTime.text = data.totalMinute.toString()

                Glide.with(this.courseCover)
                    .load(data.image)
                    .fitCenter()
                    .into(binding.courseCover)
            }

            if(data.isPremium == false) {
                binding.button.text = "Mulai Kelas"
            } else {
                binding.button.text = "Beli Rp. ${data.price}"
            }
        }
    }


}