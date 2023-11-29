package com.example.finalprojectbinar.model

data class CoursesResponses(
    val code: Int,
    val `data`: List<DataAllCourses>,
    val message: String,
    val status: String
)