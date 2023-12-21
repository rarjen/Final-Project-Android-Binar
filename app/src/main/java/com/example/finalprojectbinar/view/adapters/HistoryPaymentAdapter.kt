//package com.example.finalprojectbinar.view.adapters
//
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.finalprojectbinar.databinding.RvCardCoursesBinding
//import com.example.finalprojectbinar.model.DataAllCourses
//import com.example.finalprojectbinar.model.DataPaymentHistory
//
//class HistoryPaymentAdapter (
//    private val onButtonClick: (String, Boolean) -> Unit
//): RecyclerView.Adapter<HistoryPaymentAdapter.ViewHolder>() {
//
//    private val diffCallBack = object : DiffUtil.ItemCallback<DataPaymentHistory>() {
//        override fun areItemsTheSame(
//            oldItem: DataPaymentHistory,
//            newItem: DataPaymentHistory
//        ): Boolean = oldItem.id == newItem.id
//
//        override fun areContentsTheSame(
//            oldItem: DataPaymentHistory,
//            newItem: DataPaymentHistory
//        ): Boolean = oldItem == newItem
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): HistoryPaymentAdapter.ViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: HistoryPaymentAdapter.ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//    inner class ViewHolder(private var binding: RvCardCoursesBinding): RecyclerView.ViewHolder(binding.root) {
//
//    }
//}