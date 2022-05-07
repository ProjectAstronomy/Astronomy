package com.project.apod

import com.project.apod.domain.APODRepository
import com.project.apod.usecases.APODUseCase
import com.project.core.domain.CalendarRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class APODUseCaseTest {
    private val startDate = "2022-4-6"
    private val endDate = "2022-5-6"
    private val calendarRepository = mock<CalendarRepository> {
        on { startDate } doReturn startDate
        on { endDate } doReturn endDate
    }
    private val apodRepository = mock<APODRepository>()
    private val apodUseCase = APODUseCase(calendarRepository, apodRepository)

    @Test
    fun `check if isDateNeededToBeRefreshed is true`(): Unit = runBlocking {
        apodUseCase.loadAsync(true)
        verify(calendarRepository, times(1)).refreshDates()
    }

    @Test
    fun `check if isDateNeededToBeRefreshed is false`(): Unit = runBlocking {
        apodUseCase.loadAsync(false)
        verify(calendarRepository, never()).refreshDates()
    }

    @Test
    fun `check loadAsync() is called`(): Unit = runBlocking {
        apodUseCase.loadAsync(true)
        verify(apodRepository, times(1)).loadAsync(startDate, endDate)
    }
}