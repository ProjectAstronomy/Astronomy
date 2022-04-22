package com.project.donki.domain

import com.project.donki.entities.SolarFlare
import retrofit2.http.GET
import retrofit2.http.Query

interface FLRApiService {
    @GET("DONKI/FLR")
    suspend fun loadSolarFlare(
        @Query("start_date") startDate: String,
        @Query("end_date") end_date: String,
        @Query("api_key") apiKey: String
    ): List<SolarFlare>
}