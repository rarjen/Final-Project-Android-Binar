package com.example.finalprojectbinar.repository

import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.api.APIService
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.ProfileResponse
import com.example.finalprojectbinar.model.RegisterRequest

class MyRepository() {
    private val apiService : APIService = APIClient.instance
    // Categories
    suspend fun getCategories() = apiService.getListCategories()

    // Courses
    suspend fun getCourses(categoryId: String?, level: String?, premium: String?, search: String?) = apiService.getListCourses(categoryId, level, premium, search)
    suspend fun getDetailById(token: String?, courseId: String) = apiService.getCourseById(token ,courseId)

    // Auth
    suspend fun postLogin(loginRequest: LoginRequest) = apiService.login(loginRequest)
    suspend fun postRegister(registerRequest: RegisterRequest) = apiService.register(registerRequest)
    suspend fun validateJWT(tokenRegister: String?) = apiService.validationJWT(tokenRegister)
    suspend fun validateRegister(tokenRegister: String?, otp: String) = apiService.validateRegister(tokenRegister, otp)

    // User Profile
    suspend fun getProfile(token: String) = apiService.getProfileUser(token)

}