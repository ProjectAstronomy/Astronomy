package com.project.donki.domain.remote

import com.project.donki.domain.remote.api.GSTApiService
import com.project.donki.entities.remote.GeomagneticStorm

class GSTRepository(private val gstApiService: GSTApiService) {
    suspend fun loadAsync(startDate: String, endDate: String): List<GeomagneticStorm> =
        gstApiService.loadGeomagneticStorms(startDate, endDate)
}