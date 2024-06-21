package com.example.cs50finalprojectapp.network

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.Serializable

@Serializable
data class PostRecord(
    val date: String = "",
    @EncodeDefault val distance: Float = 0.0f,
    val time: String = "",
    @EncodeDefault val comment: String = ""
)
