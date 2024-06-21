package com.example.cs50finalprojectapp.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface FlaskApiService {
    @GET("summary")
    suspend fun getSummary() : Summary

    @GET("records")
    suspend fun getAllRecords() : List<ActivityRecord>

    @DELETE("records/{id}")
    suspend fun deleteRecord(
        @Path("id") id : Int
    ) : Unit

    @POST("records")
    suspend fun postRecord(
        @Body record: PostRecord
    ): Unit
}