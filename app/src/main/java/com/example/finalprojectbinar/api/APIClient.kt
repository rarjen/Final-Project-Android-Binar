package com.example.finalprojectbinar.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val BASE_URL = "https://binar-finalproject-backend.et.r.appspot.com/v1/"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    fun getUrlBase(): String {
        return BASE_URL
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getRetrofitBuilder(): Retrofit {
        return createRetrofit()
    }

    val instance: APIService by lazy {
        createRetrofit().create(APIService::class.java)
    }
}