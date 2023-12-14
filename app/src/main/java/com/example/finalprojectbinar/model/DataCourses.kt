package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class DataCourses(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("level")
    val level: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("totalModule")
    val totalModule: Int,
    @SerializedName("totalMinute")
    val totalMinute: Int,
    @SerializedName("isPremium")
    val isPremium: Boolean,
    @SerializedName("courseCode")
    val courseCode: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("classTarget")
    val classTarget: List<String>,
    @SerializedName("telegram")
    val telegram: String,
    @SerializedName("introVideo")
    val introVideo: String,
    @SerializedName("onboarding")
    val onboarding: String,
    @SerializedName("classModule")
    val classModule: List<ClassModule>
)