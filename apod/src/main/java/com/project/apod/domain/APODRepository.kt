package com.project.apod.domain

import com.project.apod.BuildConfig
import com.project.apod.entities.APODResponse
import retrofit2.Response

class APODRepository(private val apodApiService: APODApiService) {
    suspend fun getAPODByDate(date: String): Response<APODResponse> =
        apodApiService.getAPODByDate(date, BuildConfig.NASA_API_KEY)

    suspend fun getAPODFromDateToDate(startDate: String, endDate: String): List<APODResponse> =
        apodApiService.getAPODFromDateToDate(startDate, endDate, BuildConfig.NASA_API_KEY)
}