package com.example.finalprojectbinar.model

data class DataAllCourses(
    val author: String,
    val category: String,
    val classCode: String,
    val id: String,
    val image: String,
    val isPremium: Boolean,
    val level: String,
    val name: String,
    val price: Int,
    val rating: Double,
    val totalMinute: Int,
    val totalModule: Int
)