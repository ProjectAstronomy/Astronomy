package com.project.apod.domain.remote

import com.project.apod.entities.remote.APODResponse

class APODRepository(private val apodApiService: APODApiService) {
    suspend fun loadAsync(startDate: String, endDate: String): List<APODResponse> =
        apodApiService.getAPODFromDateToDate(startDate, endDate)
}