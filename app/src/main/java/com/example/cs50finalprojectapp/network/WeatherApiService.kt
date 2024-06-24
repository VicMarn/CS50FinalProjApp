package com.example.cs50finalprojectapp.network

import com.example.cs50finalprojectapp.network.weathermodels.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/v1/forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ) : Response<WeatherModel>
}