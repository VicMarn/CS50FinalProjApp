package com.example.cs50finalprojectapp.network.weathermodels

import kotlinx.serialization.Serializable

@Serializable
data class WeatherModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)