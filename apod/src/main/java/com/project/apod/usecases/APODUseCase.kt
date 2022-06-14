package com.project.apod.usecases

import com.project.apod.domain.local.APODRepositoryLocal
import com.project.apod.domain.remote.APODRepository
import com.project.apod.entities.remote.APODResponse
import com.project.core.domain.CalendarRepository
import com.project.core.net.AndroidNetworkStatus

class APODUseCase(
    private val calendarRepository: CalendarRepository,
    private val remoteRepository: APODRepository,
    private val localRepository: APODRepositoryLocal,
    private val androidNetworkStatus: AndroidNetworkStatus
) {
    suspend fun load(): List<APODResponse> {
        return if (!androidNetworkStatus.isNetworkAvailable()) {
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