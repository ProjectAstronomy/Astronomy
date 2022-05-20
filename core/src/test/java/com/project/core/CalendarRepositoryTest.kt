package com.project.core

import com.project.core.domain.CalendarRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class CalendarRepositoryTest {
    private lateinit var calendar: Calendar
    private lateinit var calendarRepository: CalendarRepository

    @Before
    fun setup() {
        calendar = Calendar.getInstance()
        calendarRepository = CalendarRepository(calendar)
    }

    @Test
    fun `check initial values of endDate and startDate`() {
        assertEquals(calendarRepository.endDate, "2022-5-4")
        assertEquals(calendarRepository.startDate, "2022-5-4")
    }

    @Test
    fun `check refreshing dates`() {
        calendarRepository.refreshDates()
        assertEquals(calendarRepository.endDate, "2022-5-4")
        assertEquals(calendarRepository.startDate, "2022-4-4")
    }
}