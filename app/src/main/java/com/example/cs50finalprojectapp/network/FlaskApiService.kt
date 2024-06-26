package com.example.cs50finalprojectapp.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface FlaskApiService {
    @GET("summary")
    suspend fun getSummary() : Response<Summary>

    @GET("records")
    suspend fun getAllRecords() : Response<List<ActivityRecord>>

    @DELETE("records/{id}")
    suspend fun deleteRecord(
        @Path("id") id : Int
    ) : Response<Void>

    @POST("records")
    suspend fun postRecord(
        @Body record: PostRecord
    ): Response<Void>
}