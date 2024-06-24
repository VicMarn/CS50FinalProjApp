package com.example.cs50finalprojectapp.network.weathermodels

import kotlinx.serialization.Serializable

@Serializable
data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)