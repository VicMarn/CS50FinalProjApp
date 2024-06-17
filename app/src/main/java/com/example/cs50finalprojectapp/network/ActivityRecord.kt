package com.example.cs50finalprojectapp.network

import kotlinx.serialization.Serializable


@Serializable
data class ActivityRecord(
    val id: Int = 0,
    val date: String = "",
    val distance: Float = 0.0f,
    val time: String = "",
    val comment: String = ""
)
