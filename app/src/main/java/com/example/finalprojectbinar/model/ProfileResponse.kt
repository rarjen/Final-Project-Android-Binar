package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: ProfileData?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)