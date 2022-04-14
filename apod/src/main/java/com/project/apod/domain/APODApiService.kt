package com.project.apod.domain

import com.project.apod.entities.APODResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APODApiService {
    @GET("planetary/apod")
    suspend fun getAPODByDate(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Response<APODResponse>

    @GET("planetary/apod")
    suspend fun getAPODFromDateToDate(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Response<List<APODResponse>>

    @GET("planetary/apod")
    suspend fun getAPODByCount(
        @Query("count") count: Int,
        @Query("api_key") apiKey: String
    ): Response<List<APODResponse>>
}