package com.project.donki.usecases

import com.project.core.domain.CalendarRepository
import com.project.donki.domain.local.GSTRepositoryLocal
import com.project.donki.domain.remote.GSTRepository
import com.project.donki.entities.remote.GeomagneticStorm

class GSTUseCase(
    private val calendarRepository: CalendarRepository,
    private val remoteRepository: GSTRepository,
    private val localRepository: GSTRepositoryLocal
) {
    suspend fun load(isNetworkAvailable: Boolean): List<GeomagneticStorm> {
        return if (!isNetworkAvailable) {
            localRepository.getAll()
        } else {
            calendarRepository.refreshDates(CalendarRepository.RangeFlag.ONE_YEAR)
            remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        }
    }

    suspend fun reload(): List<GeomagneticStorm> {
        calendarRepository.refreshDates(CalendarRepository.RangeFlag.ONE_YEAR)
        return remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
    }

    suspend fun insert(geomagneticStorm: GeomagneticStorm): Unit =
        localRepository.insertGeomagneticStorm(geomagneticStorm)
}