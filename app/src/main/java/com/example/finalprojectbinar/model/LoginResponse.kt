package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("token")
    val token: String?
)