package com.example.finalprojectbinar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.finalprojectbinar.model.ClassModule
import com.example.finalprojectbinar.model.EnrollmentRequest
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.OTPRequest
import com.example.finalprojectbinar.model.PaymentRequest
import com.example.finalprojectbinar.model.RegisterRequest
import com.example.finalprojectbinar.model.UpdatePasswordRequest
import com.example.finalprojectbinar.model.UpdateProfileRequest
import com.example.finalprojectbinar.repository.MyRepository
import com.example.finalprojectbinar.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


class MyViewModel(private val repository: MyRepository) : ViewModel() {

    fun getCourseCategories() = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.getCategories()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getAllCourses(categoryId: String?, level: String?, premium: String?, search: String?) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.getCourses(categoryId, level, premium, search)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getDetailByIdCourse(token: String?, coursesId: String) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.getDetailById(token, coursesId)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun postLogin(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        try {
            val response = repository.postLogin(loginRequest)
            emit(Resource.success(data = response))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun postRegister(registerRequest: RegisterRequest) = liveData(Dispatchers.IO) {
        try {
            val response = repository.postRegister(registerRequest)
            emit(Resource.success(data = response))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun validateJWT(tokenRegister: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repository.validateJWT(tokenRegister)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun validateRegister(tokenRegister: String?, otp: OTPRequest) = liveData(Dispatchers.IO){
        try {
            val response = repository.validateRegister(tokenRegister, otp)
            emit(Resource.success(data = response))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }

    }

    fun getProfileUser(token: String) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.getProfile(token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun postEnrollment(token: String?, course_uuid: EnrollmentRequest) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.postEnrollment(token, course_uuid)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun putPayment(token: String?, paymentUuid: String, payment_method: PaymentRequest) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repository.putPayment(token, paymentUuid, payment_method)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getHistoryPayment(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repository.getHistoryPayment(token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun getVideoLink(token: String?, chapterModuleUuid: String) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.getVideoLink(token, chapterModuleUuid)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun updateCompletedModule(token: String, userChapterModuleUuid: String) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.updateCompletedModule(token, userChapterModuleUuid)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun updateProfile(token: String, updateProfileRequest: UpdateProfileRequest) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.updateProfile(token, updateProfileRequest)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun updatePassword(token: String, updatePasswordRequest: UpdatePasswordRequest) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repository.updatePassword(token, updatePasswordRequest)))
        } catch (e: HttpException) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
            
    fun getNotification(token: String?) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(data = repository.getNotification(token)))
        }catch (e:Exception){
            emit(Resource.error(data = null, message = e.message ?: "Error Occured!"))
        }
    }
}