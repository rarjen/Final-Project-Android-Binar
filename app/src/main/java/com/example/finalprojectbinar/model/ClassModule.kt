package com.example.finalprojectbinar.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClassModule(
    @SerializedName("chapter")
    val chapter: String,

    @SerializedName("estimation")
    val estimation: Int,

    @SerializedName("module")
    val module: List<Module>
): Parcelable