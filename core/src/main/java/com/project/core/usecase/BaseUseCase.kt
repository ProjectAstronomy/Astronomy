package com.project.core.usecase

import com.project.core.domain.BaseRepository
import com.project.core.domain.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<T>(
    private val calendarRepository: CalendarRepository,
    private val baseRepository: BaseRepository<T>
) {
    suspend fun loadAsync(isDateNeededToBeRefreshed: Boolean): T {
        var result: T
        val startDate: String
        val endDate: String

        with(calendarRepository) {
            if (isDateNeededToBeRefreshed) refreshDates()
            startDate = this.startDate
            endDate = this.endDate
        }

        withContext(Dispatchers.IO) {
            result = baseRepository.loadAsync(startDate, endDate)
        }

        return result
    }
}