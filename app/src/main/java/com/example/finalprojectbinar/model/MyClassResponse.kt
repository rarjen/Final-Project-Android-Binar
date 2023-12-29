package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class MyClassResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<DataMyClass>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)