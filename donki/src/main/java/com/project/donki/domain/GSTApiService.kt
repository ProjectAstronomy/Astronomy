package com.project.donki.domain

import com.project.donki.entities.GeomagneticStorm
import retrofit2.http.GET
import retrofit2.http.Query

interface GSTApiService {
    @GET("DONKI/GST")
    suspend fun loadGeomagneticStorms(
        @Query("start_date") startDate: String,
        @Query("end_date") end_date: String,
        @Query("api_key") apiKey: String
    ): List<GeomagneticStorm>
}