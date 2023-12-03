package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.ListCategoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("course-categories")
    suspend fun getListCategories(): ListCategoriesResponse

    @GET("courses")
    suspend fun getListCourses(): CoursesResponses

    @GET("course/{courseId}")
    suspend fun getCourseById(@Path("courseId") courseId: String): CoursesResponsebyName

    @GET("courses/filter")
    suspend fun getCoursesByCategory(
        @Query("categoryId") categoryId: String,
        @Query("level") level: String
    ): ListCategoriesResponse
}