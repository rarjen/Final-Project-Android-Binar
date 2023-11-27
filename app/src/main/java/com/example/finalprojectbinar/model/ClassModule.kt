package com.example.finalprojectbinar.model

import com.google.gson.annotations.SerializedName

data class ClassModule(
    @SerializedName("chapter")
    val chapter: String,

    @SerializedName("estimation")
    val estimation: Int,

    @SerializedName("module")
    val module: List<Module>
)