package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class CoursesResponsebyName(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: DataCourses?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)