package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.ListCategoriesResponse
import retrofit2.http.GET

interface APIService {

    @GET("categories")
    suspend fun getListCategories(): ListCategoriesResponse
}