package com.example.cs50finalprojectapp.network

import com.example.cs50finalprojectapp.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitInstance {
    private const val FLASK_BASE_URL = BuildConfig.FLASK_URL
    private const val WEATHER_BASE_URL = "https://api.weatherapi.com"

    private val flaskRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(FLASK_BASE_URL)
            .build()
    }

    val retrofitService: FlaskApiService by lazy {
        flaskRetrofit.create(FlaskApiService::class.java)
    }

    private val weatherRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(WEATHER_BASE_URL)
            .build()
    }

    val weatherRetrofitService: WeatherApiService by lazy {
        weatherRetrofit.create(WeatherApiService::class.java)
    }
}