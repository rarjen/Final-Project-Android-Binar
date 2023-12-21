package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class Module(
    @SerializedName("title")
    val title: String,

    @SerializedName("courseLink")
    val courseLink: String
)