package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class ListCategoriesResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<DataCategories?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)