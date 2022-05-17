package com.project.donki.domain.remote.api

import com.project.donki.entities.remote.SolarFlare
import retrofit2.http.GET
import retrofit2.http.Query

interface FLRApiService {
    @GET("DONKI/FLR")
    suspend fun loadSolarFlare(
        @Query("start_date") startDate: String,
        @Query("end_date") end_date: String
    ): List<SolarFlare>
}