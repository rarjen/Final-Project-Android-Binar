package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class ValidationJWTResponse(
    @SerializedName("data")
    val `data`: DataValidationJWT?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)