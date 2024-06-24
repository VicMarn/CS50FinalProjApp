package com.example.cs50finalprojectapp.network.weathermodels

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val forecastday: List<Forecastday>
)