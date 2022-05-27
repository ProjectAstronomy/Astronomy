package com.project.apod.usecases

import android.util.Log
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
            Log.d("TAG", "555 APOD load start ${calendarRepository.startDate} end ${calendarRepository.endDate}")
            remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        }
    }

    suspend fun reload(): List<APODResponse> {
        calendarRepository.refreshDates(CalendarRepository.RangeFlag.ONE_MONTH)
        Log.d("TAG", "555 APOD reload start ${calendarRepository.startDate} end ${calendarRepository.endDate}")
        val testList = remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        Log.d("TAG", "555 APOD list__$testList")
        return testList
    }

    suspend fun insert(apodResponse: APODResponse): Unit = localRepository.insert(apodResponse)
}