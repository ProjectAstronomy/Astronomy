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
            Log.d("TAG", "777 FLR_load_start_${calendarRepository.startDate}, end ${calendarRepository.endDate}")
            remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        }
    }

    suspend fun reload(): List<SolarFlare> {
        calendarRepository.refreshDates(rangeFlag = CalendarRepository.RangeFlag.ONE_MONTH)
        Log.d("TAG", "777 FLR reload start ${calendarRepository.startDate}, end ${calendarRepository.endDate}")
        val testList = remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        Log.d("TAG", "777_testList__${testList}")
        return testList
    }

    suspend fun insert(solarFlare: SolarFlare): Unit = localRepository.insertSolarFlare(solarFlare)
}