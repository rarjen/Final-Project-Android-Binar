package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class DataCategories(
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?
)