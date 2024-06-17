package com.example.cs50finalprojectapp.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitInstance {
    private const val FLASK_BASE_URL = "http://192.168.0.193:5000"

    private val flaskRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(FLASK_BASE_URL)
            .build()
    }

    val retrofitService: FlaskApiService by lazy {
        flaskRetrofit.create(FlaskApiService::class.java)
    }
}