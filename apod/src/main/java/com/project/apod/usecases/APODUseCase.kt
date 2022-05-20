package com.project.apod.usecases

import com.project.apod.domain.local.APODRepositoryLocal
import com.project.apod.domain.remote.APODRepository
import com.project.apod.entities.remote.APODResponse
import com.project.core.domain.CalendarRepository

class APODUseCase(
    private val calendarRepository: CalendarRepository,
    private val remoteRepository: APODRepository,
    private val localRepository: APODRepositoryLocal
) {
    suspend fun load(isNetworkAvailable: Boolean): List<APODResponse> {
        return if (!isNetworkAvailable) {
            localRepository.getAll()
        } else {
            calendarRepository.refreshDates(CalendarRepository.RangeFlag.ONE_MONTH)
            remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        }
    }

    suspend fun reload(): List<APODResponse> {
        calendarRepository.refreshDates(CalendarRepository.RangeFlag.ONE_MONTH)
        return remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
    }

    suspend fun insert(apodResponse: APODResponse): Unit = localRepository.insert(apodResponse)
}