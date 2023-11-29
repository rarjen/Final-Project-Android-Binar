package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class CoursesResponsebyName(
    @SerializedName("link")
    val code: Int,
    @SerializedName("data")
    val `data`: List<DataCourses>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)