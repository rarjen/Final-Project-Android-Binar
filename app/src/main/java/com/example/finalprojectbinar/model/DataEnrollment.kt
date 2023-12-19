package com.example.finalprojectbinar.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataEnrollment(
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
) : Parcelable