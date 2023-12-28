package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class DataNotification(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_uuid")
    val user_uuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("notification")
    val notification: String,
    @SerializedName("is_conditional")
    val is_conditional: Boolean,
    @SerializedName("is_read")
    val is_read: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
)