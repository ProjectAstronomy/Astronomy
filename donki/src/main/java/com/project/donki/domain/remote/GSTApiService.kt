package com.project.donki.domain.remote

import com.project.donki.entities.remote.GeomagneticStorm
import retrofit2.http.GET
import retrofit2.http.Query

interface GSTApiService {
    @GET("DONKI/GST")
    suspend fun loadGeomagneticStorms(
        @Query("start_date") startDate: String,
        @Query("end_date") end_date: String
    ): List<GeomagneticStorm>
}