package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: DataPayment?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)