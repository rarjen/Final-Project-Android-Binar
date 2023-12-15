package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class ValidationRegisterResponse(
    @SerializedName("data")
    val `data`: DataValidationResponse?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)