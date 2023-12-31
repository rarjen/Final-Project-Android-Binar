package com.example.finalprojectbinar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.api.APIService
import com.example.finalprojectbinar.model.EnrollmentRequest
import com.example.finalprojectbinar.model.ForgetPasswordRequest
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.OTPRequest
import com.example.finalprojectbinar.model.PaymentRequest
import com.example.finalprojectbinar.model.ProfileResponse
import com.example.finalprojectbinar.model.RegisterRequest
import com.example.finalprojectbinar.model.UpdatePasswordRequest
import com.example.finalprojectbinar.model.UpdateProfileRequest

class MyRepository() {
    private val apiService : APIService = APIClient.instance

    // Categories
    suspend fun getCategories() = apiService.getListCategories()

    // Courses
    suspend fun getCourses(categoryId: String?, level: String?, premium: String?, search: String?) = apiService.getListCourses(categoryId, level, premium, search)
    suspend fun getDetailById(token: String?, courseId: String) = apiService.getCourseById(token ,courseId)
    suspend fun getMyClass(token: String?, isComplete: String?) = apiService.getMyClass(token, isComplete)

    // Auth
    suspend fun postLogin(loginRequest: LoginRequest) = apiService.login(loginRequest)
    suspend fun postRegister(registerRequest: RegisterRequest) = apiService.register(registerRequest)
    suspend fun validateJWT(tokenRegister: String?) = apiService.validationJWT(tokenRegister)
    suspend fun validateRegister(tokenRegister: String?, otp: OTPRequest) = apiService.validateRegister(tokenRegister, otp)
    suspend fun postForgetPassword(email:String)= apiService.forgetPassword(ForgetPasswordRequest(email))

    // User Profile
    suspend fun getProfile(token: String) = apiService.getProfileUser(token)

    //Enrollment
    suspend fun postEnrollment(token: String?, course_uuid: EnrollmentRequest) = apiService.postEnrollment(token, course_uuid)

    //Payment
    suspend fun putPayment(token: String?, paymentUuid: String, payment_method: PaymentRequest) = apiService.putPayment(token, paymentUuid, payment_method)
    suspend fun getHistoryPayment(token: String) = apiService.getHistoryPayment(token)

    //Video
    suspend fun getVideoLink(token: String?, chapterModuleUuid: String) = apiService.getVideoLink(token, chapterModuleUuid)

    //Module
    suspend fun updateCompletedModule(token: String, userChapterModuleUuid: String) = apiService.updateCompletedModule(token, userChapterModuleUuid)

    //User Management
    suspend fun updateProfile(token: String, updateProfileRequest: UpdateProfileRequest) = apiService.updateProfile(token, updateProfileRequest)
    suspend fun updatePassword(token: String, updatePasswordRequest: UpdatePasswordRequest) = apiService.updatePassword(token, updatePasswordRequest)
    suspend fun getNotification(token: String?) = apiService.getNotification(token)

}