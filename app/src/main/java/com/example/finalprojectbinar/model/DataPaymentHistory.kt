package com.example.finalprojectbinar.model


import com.google.gson.annotations.SerializedName

data class DataPaymentHistory(
    @SerializedName("courseUuid")
    val courseUuid: String?,
    @SerializedName("paymentUuid")
    val paymentUuid: String?,
    @SerializedName("userCourseId")
    val userCourseId: String?,
    @SerializedName("courseAuthor")
    val courseAuthor: String?,
    @SerializedName("courseCategory")
    val courseCategory: String?,
    @SerializedName("courseLevel")
    val courseLevel: String?,
    @SerializedName("courseName")
    val courseName: String?,
    @SerializedName("courseRating")
    val courseRating: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_paid")
    val isPaid: Boolean?,
    @SerializedName("totalMinutes")
    val totalMinutes: Int?,
    @SerializedName("totalModules")
    val totalModules: Int?
)