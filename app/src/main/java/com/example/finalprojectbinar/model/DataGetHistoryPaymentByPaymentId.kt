package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class DataGetHistoryPaymentByPaymentId(
    @SerializedName("author")
    val author: String?,
    @SerializedName("expired")
    val expired: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("paid")
    val paid: Boolean?,
    @SerializedName("paymentUuid")
    val paymentUuid: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("tax")
    val tax: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("type")
    val type: String?
)