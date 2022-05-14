package com.project.donki.domain.remote

import com.project.core.domain.BaseRepository
import com.project.donki.entities.remote.GeomagneticStorm

class GSTRepository(private val gstApiService: GSTApiService) :
    BaseRepository<List<GeomagneticStorm>> {

    override suspend fun loadAsync(startDate: String, endDate: String): List<GeomagneticStorm> =
        gstApiService.loadGeomagneticStorms(startDate, endDate)
}