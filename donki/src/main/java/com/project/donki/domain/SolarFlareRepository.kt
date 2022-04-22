package com.project.donki.domain

import com.project.core.domain.BaseRepository
import com.project.donki.BuildConfig
import com.project.donki.entities.SolarFlareResponse

class SolarFlareRepository(private val solarFlareApiService: SolarFlareApiService) :
    BaseRepository<List<SolarFlareResponse>> {

    override suspend fun loadAsync(startDate: String, endDate: String): List<SolarFlareResponse> =
        solarFlareApiService.loadSolarFlare(startDate, endDate, BuildConfig.NASA_API_KEY)
}