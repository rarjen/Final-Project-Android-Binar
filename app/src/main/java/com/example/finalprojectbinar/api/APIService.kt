package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.LoginResponse
import com.example.finalprojectbinar.model.ProfileResponse
import com.example.finalprojectbinar.model.RegisterRequest
import com.example.finalprojectbinar.model.RegisterResponse
import com.example.finalprojectbinar.model.ValidationJWTResponse
import com.example.finalprojectbinar.model.ValidationRegisterResponse
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
    suspend fun getCourseById(
        @Header("Authorization") token: String?,
        @Path("courseId") courseId: String
    ): CoursesResponsebyName

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("validate-register")
    suspend fun validateRegister(
        @Header("Authorization") tokenRegister: String?,
        @Field("otp") otp: String
    ): ValidationRegisterResponse

    @GET("validate-jwt")
    suspend fun validationJWT(
        @Header("Authorization") tokenRegister: String?
    ): ValidationJWTResponse

    @GET("profile")
    suspend fun getProfileUser(
        @Header("Authorization") token: String
    ): ProfileResponse

}