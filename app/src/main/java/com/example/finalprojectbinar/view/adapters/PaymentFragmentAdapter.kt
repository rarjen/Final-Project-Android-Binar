package com.example.finalprojectbinar.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.databinding.ActivityPaymentBinding
import com.example.finalprojectbinar.model.DataCourses

class PaymentFragmentAdapter(): RecyclerView.Adapter<PaymentFragmentAdapter.ViewHolder>() {

    private var courseData: DataCourses? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentFragmentAdapter.ViewHolder {
        TODO()
    }

    override fun onBindViewHolder(holder: PaymentFragmentAdapter.ViewHolder, position: Int) {
        TODO()
    }

    override fun getItemCount(): Int {
        TODO()
    }

    inner class ViewHolder(private var binding: ActivityPaymentBinding): RecyclerView.ViewHolder(binding.root){
    }

}
