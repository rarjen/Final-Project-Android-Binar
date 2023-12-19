package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class EnrollmentResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: DataEnrollment?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)