package com.example.finalprojectbinar.model

data class UpdateProfileRequest(
    val name: String?,
    val phone: String?,
    val address: String?,
    val country: String?,
    val city: String?
)
