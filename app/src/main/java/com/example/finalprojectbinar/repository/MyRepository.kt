package com.example.finalprojectbinar.repository

import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.api.APIService

class MyRepository {
    private val apiService : APIService = APIClient.instance
    // Categories
    suspend fun getCategories() = apiService.getListCategories()

    suspend fun getCourses(categoryId: String?, level: String?, premium: String?, search: String?) = apiService.getListCourses(categoryId, level, premium, search)

    suspend fun getDetailById(courseId: String) = apiService.getCourseById(courseId)
}