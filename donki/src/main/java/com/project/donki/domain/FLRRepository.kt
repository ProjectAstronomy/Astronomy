package com.project.donki.domain

import com.project.core.domain.BaseRepository
import com.project.donki.entities.SolarFlare

class FLRRepository(private val flrApiService: FLRApiService) :
    BaseRepository<List<SolarFlare>> {

    override suspend fun loadAsync(startDate: String, endDate: String): List<SolarFlare> =
        flrApiService.loadSolarFlare(startDate, endDate)
}