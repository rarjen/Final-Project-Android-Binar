package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class GetHistoryPaymentByPaymentIdResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: DataGetHistoryPaymentByPaymentId?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)