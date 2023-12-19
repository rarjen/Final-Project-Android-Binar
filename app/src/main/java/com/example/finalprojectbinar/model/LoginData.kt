package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("token")
    val token: String?
)