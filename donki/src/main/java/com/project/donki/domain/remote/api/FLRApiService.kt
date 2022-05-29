package com.project.donki.domain.remote.api

import com.project.donki.entities.remote.SolarFlare
import retrofit2.http.GET
import retrofit2.http.Query

interface FLRApiService {
    @GET("DONKI/FLR")
    suspend fun loadSolarFlare(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): List<SolarFlare>
}