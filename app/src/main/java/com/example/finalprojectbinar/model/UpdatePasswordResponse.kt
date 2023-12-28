package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class UpdatePasswordResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)