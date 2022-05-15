package com.project.donki.domain

import com.project.donki.entities.GeomagneticStorm
import retrofit2.http.GET
import retrofit2.http.Query

interface GSTApiService {
    @GET("DONKI/GST")
    suspend fun loadGeomagneticStorms(
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): List<GeomagneticStorm>
}