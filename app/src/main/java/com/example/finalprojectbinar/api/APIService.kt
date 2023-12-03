package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.ListCategoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("course-categories")
    suspend fun getListCategories(): ListCategoriesResponse

    @GET("courses")
    suspend fun getListCourses(): CoursesResponses

    @GET("course")
    suspend fun getCourseById(@Query("courseId") courseId: String): CoursesResponsebyName
}