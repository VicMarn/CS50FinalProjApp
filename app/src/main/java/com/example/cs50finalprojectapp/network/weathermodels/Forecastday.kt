package com.example.cs50finalprojectapp.network.weathermodels

import kotlinx.serialization.Serializable

@Serializable
data class Forecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>
)