package com.example.finalprojectbinar.repository

import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.api.APIService

class MyRepository {
    private val apiService : APIService = APIClient.instance
    // Categories
    suspend fun getCategories() = apiService.getListCategories()

    suspend fun getCourses() = apiService.getListCourses()

    suspend fun getDetailById(courseId: String) = apiService.getCourseById(courseId)
}