package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class ForgetPasswordResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DataRegister?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
)
