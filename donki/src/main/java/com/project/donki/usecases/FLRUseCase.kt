package com.project.donki.usecases

import android.util.Log
import com.project.core.domain.CalendarRepository
import com.project.donki.domain.remote.FLRRepository
import com.project.donki.domain.local.FLRRepositoryLocal
import com.project.donki.entities.remote.SolarFlare

class FLRUseCase(
    private val calendarRepository: CalendarRepository,
    private val remoteRepository: FLRRepository,
    private val localRepository: FLRRepositoryLocal
) {
    suspend fun load(isNetworkAvailable: Boolean): List<SolarFlare> {
        return if (!isNetworkAvailable) {
            localRepository.getAll()
        } else {
            calendarRepository.refreshDates(rangeFlag = CalendarRepository.RangeFlag.ONE_MONTH)
            Log.d("TAG", "55555555555_load__${calendarRepository.startDate}, ${calendarRepository.endDate}")
            remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        }
    }

    suspend fun reload(): List<SolarFlare> {
        calendarRepository.refreshDates(rangeFlag = CalendarRepository.RangeFlag.ONE_MONTH)
        Log.d("TAG", "55555555555_reload__${calendarRepository.startDate}, ${calendarRepository.endDate}")
        return remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
    }

    suspend fun insert(solarFlare: SolarFlare): Unit = localRepository.insertSolarFlare(solarFlare)
}