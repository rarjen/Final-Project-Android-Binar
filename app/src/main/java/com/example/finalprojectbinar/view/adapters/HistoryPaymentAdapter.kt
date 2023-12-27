package com.example.finalprojectbinar.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.RvCardCoursesBinding
import com.example.finalprojectbinar.model.DataPaymentHistory

class HistoryPaymentAdapter (
    private val onButtonClick: (String, String, Boolean) -> Unit
): RecyclerView.Adapter<HistoryPaymentAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<DataPaymentHistory>() {
        override fun areItemsTheSame(
            oldItem: DataPaymentHistory,
            newItem: DataPaymentHistory
        ): Boolean = oldItem.courseUuid == newItem.courseUuid

        override fun areContentsTheSame(
            oldItem: DataPaymentHistory,
            newItem: DataPaymentHistory
        ): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitHistoryPayment(value: List<DataPaymentHistory>) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryPaymentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(RvCardCoursesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryPaymentAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private var binding: RvCardCoursesBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.statusLayout.setOnClickListener {
                val courseId = differ.currentList[adapterPosition].courseUuid
                val isPaid = differ.currentList[adapterPosition].isPaid
                val paymentId = differ.currentList[adapterPosition].paymentUuid

                onButtonClick(courseId!!, paymentId!!, isPaid!!)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(data: DataPaymentHistory){
            binding.apply {
                binding.tvCardTitleCourse.text = data.courseName
                binding.tvCardAuthorCourse.text = data.courseAuthor
                binding.tvCardLevel.text = "${data.courseLevel} Level"
                binding.tvCardRate.text = data.courseRating
                binding.tvCardTotalModul.text = data.totalModules.toString()
                binding.tvCardTotalTime.text = data.totalMinutes.toString()
                binding.tvCardCategory.text = data.courseCategory
                Glide.with(this.ivCardImage)
                    .load(data.image)
                    .fitCenter()
                    .into(binding.ivCardImage)

                if(data.isPaid == false){
                    binding.statusLayout.background = ContextCompat.getDrawable(binding.root.context, R.drawable.rounded_background_unpaid)
                    binding.tvStatus.text = "Waiting for Payment"
                }
            }
        }
    }
}