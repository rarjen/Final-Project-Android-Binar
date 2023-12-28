package com.example.finalprojectbinar.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Module(
    @SerializedName("title")
    val title: String,
    @SerializedName("chapterModuleUuid")
    val chapterModuleUuid: String,
    @SerializedName("isCompleted")
    val isCompleted: Boolean,
    @SerializedName("userChapterModuleUuid")
    val userChapterModuleUuid: String?
): Parcelable