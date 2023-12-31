package com.example.finalprojectbinar.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectbinar.R
import com.example.finalprojectbinar.databinding.RvNotificationBinding
import com.example.finalprojectbinar.model.DataNotification
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
class NotificationAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val diffCallback = object: DiffUtil.ItemCallback<DataNotification>(){
        override fun areItemsTheSame(oldItem: DataNotification, newItem: DataNotification): Boolean =
            oldItem.user_uuid == newItem.user_uuid
        override fun areContentsTheSame(oldItem: DataNotification, newItem: DataNotification): Boolean =
            oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitNotifResponse(value: List<DataNotification>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RvNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val linearHolder = holder as NotificationHolder
        val data = differ.currentList[position]
        data.let {
            linearHolder.onBind(data)
        }
    }

    class NotificationHolder(val binding: RvNotificationBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(data: DataNotification){

            val timestampString = data.updatedAt
            val instant = Instant.parse(timestampString)
            val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDateTime = localDateTime.format(formatter)

            binding.rvNotifcationTitle.text = data.title
            binding.rvNotificationSubTitle.text = data.notification
            binding.rvNotificationTime.text = formattedDateTime
            if (!data.is_read){
                binding.notificationElips.background = ContextCompat.getDrawable(binding.root.context,  R.drawable.circle_background)
            }else{
                binding.notificationElips.background = ContextCompat.getDrawable(binding.root.context,  R.drawable.circle_background_2)
            }
            if(!data.is_conditional){
                binding.rvNotificationContent.visibility = View.GONE
            }else{
                binding.rvNotificationContent.visibility = View.VISIBLE
                binding.rvNotificationContent.text = "Syarat dan Ketentuan Berlaku"
            }
        }
    }

}