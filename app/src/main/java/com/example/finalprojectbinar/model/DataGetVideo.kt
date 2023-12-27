package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class DataGetVideo(
    @SerializedName("course_link")
    val courseLink: String?
)