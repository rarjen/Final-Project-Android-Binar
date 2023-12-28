package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<Int?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)