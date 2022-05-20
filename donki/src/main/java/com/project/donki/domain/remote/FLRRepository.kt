package com.project.donki.domain.remote

import com.project.donki.domain.remote.api.FLRApiService
import com.project.donki.entities.remote.SolarFlare

class FLRRepository(private val flrApiService: FLRApiService) {
    suspend fun loadAsync(startDate: String, endDate: String): List<SolarFlare> =
        flrApiService.loadSolarFlare(startDate, endDate)
}