package com.example.finalprojectbinar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.api.APIService
import com.example.finalprojectbinar.model.EnrollmentRequest
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.OTPRequest
import com.example.finalprojectbinar.model.PaymentRequest
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
    suspend fun validateRegister(tokenRegister: String?, otp: OTPRequest) = apiService.validateRegister(tokenRegister, otp)

    // User Profile
    suspend fun getProfile(token: String) = apiService.getProfileUser(token)

    //Enrollment
    suspend fun postEnrollment(token: String?, course_uuid: EnrollmentRequest) = apiService.postEnrollment(token, course_uuid)

    //Payment
    suspend fun putPayment(token: String?, paymentUuid: String, payment_method: PaymentRequest) = apiService.putPayment(token, paymentUuid, payment_method)
    suspend fun getHistoryPayment(token: String) = apiService.getHistoryPayment(token)
}