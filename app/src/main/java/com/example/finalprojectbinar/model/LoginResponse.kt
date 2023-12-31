package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: LoginData?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("status")
    val status: String?
)