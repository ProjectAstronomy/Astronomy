package com.project.apod.usecases

import com.project.apod.domain.APODRepositoryLocal
import com.project.apod.entities.APODResponse
import com.project.core.domain.BaseRepository
import com.project.core.domain.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class APODUseCase(
    private val calendarRepository: CalendarRepository,
    private val remoteRepository: BaseRepository<List<APODResponse>>,
    private val localRepository: APODRepositoryLocal
) {
    suspend fun load(isNetworkAvailable: Boolean): List<APODResponse> {
        return if (!isNetworkAvailable) {
            localRepository.getAll()
        } else {
            calendarRepository.refreshDates()
            remoteRepository.loadAsync(calendarRepository.startDate, calendarRepository.endDate)
        }
    }

    suspend fun reload(): List<APODResponse> {
        val startDate: String
        val endDate: String
        with(calendarRepository) {
            calendarRepository.refreshDates()
            startDate = this.startDate
            endDate = this.endDate
        }
        var result: List<APODResponse>
        withContext(Dispatchers.IO) {
            result = remoteRepository.loadAsync(startDate, endDate)
        }
        return result
    }

    suspend fun insert(apodResponse: APODResponse): Unit = localRepository.insert(apodResponse)
}