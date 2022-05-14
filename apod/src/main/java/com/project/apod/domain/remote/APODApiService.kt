package com.project.apod.domain.remote

import com.project.apod.entities.remote.APODResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APODApiService {
    @GET("planetary/apod")
    suspend fun getAPODFromDateToDate(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): List<APODResponse>
}