package com.example.finalprojectbinar.repository

import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.api.APIService
import com.example.finalprojectbinar.model.LoginRequest

class MyRepository() {
    private val apiService : APIService = APIClient.instance
    // Categories
    suspend fun getCategories() = apiService.getListCategories()

    // Courses
    suspend fun getCourses(categoryId: String?, level: String?, premium: String?, search: String?) = apiService.getListCourses(categoryId, level, premium, search)
    suspend fun getDetailById(courseId: String) = apiService.getCourseById(courseId)

    // Auth
    suspend fun postLogin(loginRequest: LoginRequest) = apiService.login(loginRequest)
}