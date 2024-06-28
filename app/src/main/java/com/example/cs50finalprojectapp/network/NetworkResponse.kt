package com.example.cs50finalprojectapp.network


/*This class was created with chatGTP assistance*/
sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Loading(val message: String): NetworkResponse<Nothing>()
    data class Error(val errorMessage: String): NetworkResponse<Nothing>()
}