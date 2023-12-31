package com.example.finalprojectbinar.api

import com.example.finalprojectbinar.model.CoursesResponses
import com.example.finalprojectbinar.model.DataAllCourses
import com.example.finalprojectbinar.model.DataCategories
import com.example.finalprojectbinar.model.DataRegister
import com.example.finalprojectbinar.model.ListCategoriesResponse
import com.example.finalprojectbinar.model.LoginData
import com.example.finalprojectbinar.model.LoginRequest
import com.example.finalprojectbinar.model.LoginResponse
import com.example.finalprojectbinar.model.RegisterRequest
import com.example.finalprojectbinar.model.RegisterResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.Random

class APIClientTest {
    @Mock
    private lateinit var mockApiClient: APIClient

    @Mock
    private lateinit var mockApiService: APIService

    @Before
    fun setup() {
        @Suppress("DEPRECATION")
        MockitoAnnotations.initMocks(this)
        mockApiClient = mock(APIClient::class.java)
    }

    private fun generateRandomString(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charPool[Random().nextInt(charPool.size)] }
            .joinToString("")
    }

    private fun generateRandomEmail(): String {
        val randomString = generateRandomString(8)
        return "$randomString@example.com"
    }

    @Test
    fun testBaseUrl() {
        val baseUrl = APIClient.getUrlBase()
        assertThat(APIClient.getRetrofitBuilder().baseUrl().toUrl().toString()).isEqualTo(baseUrl)
    }

    @Test
    fun testGetCategoriesSuccess() = runBlocking {
        val mockCategoriesList = listOf(DataCategories(
            id = "test",
            image = "test",
            name = "test"
        ))

        val mockResponse = ListCategoriesResponse(200, mockCategoriesList, "Success", "Ok")
        `when`(mockApiService.getListCategories()).thenReturn(mockResponse)

        val result = mockApiService.getListCategories()

        assertThat(result.code).isEqualTo(200)
        assertThat(result.status).isEqualTo("Ok")
        assertThat(result.data).isEqualTo(mockCategoriesList)
        assertThat(result.message).isEqualTo("Success")
    }

    @Test
    fun getCoursesSuccess() = runBlocking{
        val mockData = listOf(DataAllCourses(
             author = "author",
             category = "category",
             classCode = "classCode",
             id = "id",
             image = "image",
             isPremium = true,
             level = "level",
             name = "name",
             price = 20000,
             rating = 4.5,
             totalMinute = 34,
             totalModule = 10
        ))

        val mockResponse = CoursesResponses(200, mockData, "Success", "Ok")
        `when`(mockApiService.getListCourses(null, null, null, null)).thenReturn(mockResponse)

        val result = mockApiService.getListCourses(null, null, null, null)

        assertThat(result.code).isEqualTo(200)
        assertThat(result.status).isEqualTo("Ok")
        assertThat(result.data).isEqualTo(mockData)
        assertThat(result.message).isEqualTo("Success")
    }

    @Test
    fun loginSuccess() = runBlocking {
        val mockBodyRequest = LoginRequest("rarjen57@gmail.com", "password")

        val mockLoginData = LoginData(
            name = "namae",
            email = "rarjen57@gmail.com",
            phone = "085398443210",
            country = "Indonesia",
            city = "Semarang",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjNjYjc0ZGMxLTc5MmEtNDYyMC05MzU3LWVjYWNhZjcyODZlMCIsImlhdCI6MTcwMzkyNTMxNywiZXhwIjoxNzA0MDExNzE3fQ.0RqAJZZfXU6J2-fzaZFzAE_ns9_VrThfkxSy6LjPuWo"
        )
        val mockResponse = LoginResponse(status = "Ok", code = 200, message = "Successfully Login", data = mockLoginData)
        `when`(mockApiService.login(mockBodyRequest)).thenReturn(mockResponse)

        val result = mockApiService.login(mockBodyRequest)

        assertThat(result.code).isEqualTo(200)
        assertThat(result.message).isEqualTo("Successfully Login")
        assertThat(result.status).isEqualTo("Ok")
        assertThat(result.data).isNotNull()
    }

    @Test
    fun loginFailedPasswordNotMatch() = runBlocking {
        val mockBodyRequest = LoginRequest("rarjen57@gmail.com", "password1")

        val mockResponse = LoginResponse(status = "Unauthorized", code = 401, message = "Unauthorized. Password not match", data = null)
        `when`(mockApiService.login(mockBodyRequest)).thenReturn(mockResponse)

        val result = mockApiService.login(mockBodyRequest)

        assertThat(result.code).isEqualTo(401)
        assertThat(result.message).isEqualTo("Unauthorized. Password not match")
        assertThat(result.status).isEqualTo("Unauthorized")
        assertThat(result.data).isNull()
    }

    @Test
    fun loginFailedUserNotFound() = runBlocking {
        val mockBodyRequest = LoginRequest("rarjen@gmail.com", "password")

        val mockResponse = LoginResponse(status = "Unauthorized", code = 401, message = "Unauthorized. Cannot Find User", data = null)
        `when`(mockApiService.login(mockBodyRequest)).thenReturn(mockResponse)

        val result = mockApiService.login(mockBodyRequest)

        assertThat(result.code).isEqualTo(401)
        assertThat(result.message).isEqualTo("Unauthorized. Cannot Find User")
        assertThat(result.status).isEqualTo("Unauthorized")
        assertThat(result.data).isNull()
    }

    @Test
    fun registerSuccess() = runBlocking {
        val randomName = generateRandomString(10)
        val randomEmail = generateRandomEmail()
        val randomPhone = generateRandomString(12)
        val randomPassword = generateRandomString(8)

        val mockBodyRequest = RegisterRequest (
            name  =  randomName,
            email = randomEmail,
            phone = randomPhone,
            password = randomPassword
        )

        val mockDataRegister = DataRegister(
            name = "test",
            email = "test",
            phone = "test",
            country = "test",
            city = "test",
            token = "test"
        )

        val mockResponse = RegisterResponse(status = "Ok", message = "Successfully Register, please check your email to validate your account", data = mockDataRegister, code = null)
        `when`(mockApiService.register(mockBodyRequest)).thenReturn(mockResponse)

        val result = mockApiService.register(mockBodyRequest)
        assertThat(result.message).isEqualTo("Successfully Register, please check your email to validate your account")
        assertThat(result.status).isEqualTo("Ok")
        assertThat(result.code).isNull()
        assertThat(result.data).isNotNull()
    }

    @Test
    fun registerFailedUserExist() = runBlocking{
        val mockBodyRequest = RegisterRequest (
            name  =  "test nama",
            email = "rarjen57@gmail.com",
            phone = "085324997520",
            password = "password"
        )

        val mockResponse = RegisterResponse(status = "Unauthorized", message = "Unauthorized. User Already Exists", data = null, code = 401)
        `when`(mockApiService.register(mockBodyRequest)).thenReturn(mockResponse)

        val result = mockApiService.register(mockBodyRequest)

        assertThat(result.message).isEqualTo("Unauthorized. User Already Exists")
        assertThat(result.status).isEqualTo("Unauthorized")
        assertThat(result.code).isEqualTo(401)
        assertThat(result.data).isNull()
    }
}