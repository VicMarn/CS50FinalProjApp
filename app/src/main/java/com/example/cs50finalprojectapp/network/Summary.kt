package com.example.cs50finalprojectapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    @SerialName(value = "number_of_days")
    val numberOfDays: Int = 0,

    @SerialName(value = "total_distance")
    val totalDistance: Float = 0.0f,

    @SerialName(value = "total_time")
    val totalTime: Int = 0,

)
