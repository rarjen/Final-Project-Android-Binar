package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val `data`: List<DataNotification>,


)