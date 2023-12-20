package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class DataPayment(
    @SerializedName("author")
    val author: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("paid")
    val paid: Boolean?,
    @SerializedName("paymentMethod")
    val paymentMethod: String?,
    @SerializedName("paymentUuid")
    val paymentUuid: String?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("type")
    val type: String?
)