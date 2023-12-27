package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class GetVideoResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: DataGetVideo?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)