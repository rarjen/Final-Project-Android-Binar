package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class DataMyClass(
    @SerializedName("author")
    val author: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("classCode")
    val classCode: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isPremium")
    val isPremium: Boolean?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("progressBar")
    val progressBar: Int?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("totalMinute")
    val totalMinute: Int?,
    @SerializedName("totalModule")
    val totalModule: Int?
)