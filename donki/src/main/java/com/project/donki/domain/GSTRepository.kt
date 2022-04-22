package com.project.donki.domain

import com.project.core.domain.BaseRepository
import com.project.donki.BuildConfig
import com.project.donki.entities.GeomagneticStorm

class GSTRepository(private val gstApiService: GSTApiService) :
    BaseRepository<List<GeomagneticStorm>> {

    override suspend fun loadAsync(startDate: String, endDate: String): List<GeomagneticStorm> =
        gstApiService.loadGeomagneticStorms(startDate, endDate, BuildConfig.NASA_API_KEY)
}