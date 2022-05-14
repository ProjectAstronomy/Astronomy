package com.project.apod.domain.remote

import com.project.apod.entities.remote.APODResponse
import com.project.core.domain.BaseRepository

class APODRepository(private val apodApiService: APODApiService) : BaseRepository<List<APODResponse>> {
    override suspend fun loadAsync(startDate: String, endDate: String): List<APODResponse> =
        apodApiService.getAPODFromDateToDate(startDate, endDate)
}