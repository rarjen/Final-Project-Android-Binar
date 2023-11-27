package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class Module(
    @SerializedName("link")
    val link: String,

    @SerializedName("title")
    val title: String
)