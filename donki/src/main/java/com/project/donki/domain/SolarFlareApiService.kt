package com.project.donki.domain

import com.project.donki.entities.SolarFlareResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SolarFlareApiService {
    @GET("DONKI/FLR")
    suspend fun loadSolarFlare(
        @Query("start_date") startDate: String,
        @Query("end_date") end_date: String,
        @Query("api_key") apiKey: String
    ): List<SolarFlareResponse>
}