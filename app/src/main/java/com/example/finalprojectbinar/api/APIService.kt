package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.LoginResponse
import retrofit2.http.*


interface APIService {

    @GET("course-categories")
    suspend fun getListCategories(): ListCategoriesResponse

    @GET("courses")
    suspend fun getListCourses(
        @Query("categoryId") categoryId: String?,
        @Query("level") level: String?,
        @Query("premium") premium: String?,
        @Query("search") search: String?,
    ): CoursesResponses

    @GET("course/{courseId}")
    suspend fun getCourseById(@Path("courseId") courseId: String): CoursesResponsebyName

    @POST("login")
    suspend fun login(@Body request: LoginRequest
    ): LoginResponse

}