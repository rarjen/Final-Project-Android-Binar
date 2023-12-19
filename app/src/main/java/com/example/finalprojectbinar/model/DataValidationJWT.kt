package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class DataValidationJWT(
    @SerializedName("expiredAt")
    val expiredAt: String?
)