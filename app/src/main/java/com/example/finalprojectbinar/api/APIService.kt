package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.CompletedModuleResponse
import com.example.finalprojectbinar.model.CoursesResponsebyName
import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.DataNotification
import com.example.finalprojectbinar.model.EnrollmentRequest
import com.example.finalprojectbinar.model.EnrollmentResponse
import com.example.finalprojectbinar.model.GetVideoResponse
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.LoginResponse
import com.example.finalprojectbinar.model.NotificationResponse
import com.example.finalprojectbinar.model.OTPRequest
import com.example.finalprojectbinar.model.PaymentHistoryResponse
import com.example.finalprojectbinar.model.PaymentRequest
import com.example.finalprojectbinar.model.PaymentResponse
import com.example.finalprojectbinar.model.ProfileResponse
import com.example.finalprojectbinar.model.RegisterRequest
import com.example.finalprojectbinar.model.RegisterResponse
import com.example.finalprojectbinar.model.UpdatePasswordRequest
import com.example.finalprojectbinar.model.UpdatePasswordResponse
import com.example.finalprojectbinar.model.UpdateProfileRequest
import com.example.finalprojectbinar.model.UpdateProfileResponse
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

    @POST("validate-register")
    suspend fun validateRegister(
        @Header("Authorization") tokenRegister: String?,
        @Body request: OTPRequest
    ): ValidationRegisterResponse

    @GET("validate-jwt")
    suspend fun validationJWT(
        @Header("Authorization") tokenRegister: String?
    ): ValidationJWTResponse

    @GET("profile")
    suspend fun getProfileUser(
        @Header("Authorization") token: String
    ): ProfileResponse

    @POST("courses/enrollment")
    suspend fun postEnrollment(
        @Header("Authorization") token: String?,
        @Body enrollmentRequest: EnrollmentRequest
    ): EnrollmentResponse

    @PUT("courses/payment/{paymentUuid}")
    suspend fun putPayment(
        @Header("Authorization") token: String?,
        @Path("paymentUuid") paymentUuid: String,
        @Body paymentRequest: PaymentRequest
    ): PaymentResponse

    @GET("courses/payment-history")
    suspend fun getHistoryPayment(
        @Header("Authorization") token: String?
    ): PaymentHistoryResponse

    @GET("courses/video-course/{chapterModuleUuid}")
    suspend fun getVideoLink(
        @Header("Authorization") token: String?,
        @Path("chapterModuleUuid") chapterModuleUuid: String
    ): GetVideoResponse

    @PUT("course-modules/module-completed/{userChapterModuleUuid}")
    suspend fun updateCompletedModule(
        @Header("Authorization") token: String?,
        @Path("userChapterModuleUuid") userChapterModuleUuid: String
    ): CompletedModuleResponse

    @PUT("profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String?,
        @Body updateProfileRequest: UpdateProfileRequest
    ): UpdateProfileResponse

    @PUT("update-password")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body updatePasswordRequest: UpdatePasswordRequest
    ): UpdatePasswordResponse
  
    @GET("notification")
    suspend fun getNotification(
        @Header("Authorization") token: String?
    ): NotificationResponse
}