package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class PaymentHistoryResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<DataPaymentHistory>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)