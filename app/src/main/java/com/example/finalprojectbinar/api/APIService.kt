package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.ListCategoriesResponse
import retrofit2.http.GET

interface APIService {

    @GET("course-categories")
    suspend fun getListCategories(): ListCategoriesResponse

    @GET("courses")
    suspend fun getListCourses(): CoursesResponses
}