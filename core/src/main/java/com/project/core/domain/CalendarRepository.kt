package com.project.core.domain

import java.util.*

class CalendarRepository(private val calendar: Calendar) {
    enum class RangeFlag {
        ONE_MONTH, TWO_MONTHS, ONE_YEAR
    }

    private var endDateYear: Int = calendar.get(Calendar.YEAR)
    private var endDateMonth: Int = calendar.get(Calendar.MONTH) + 1
    private var endDateDayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

    private var startDateYear: Int = endDateYear
    private var startDateMonth: Int = endDateMonth
    private var startDateDayOfMonth: Int = endDateDayOfMonth

    var endDate: String = ""
        get() = "$endDateYear-$endDateMonth-$endDateDayOfMonth"
        private set

    var startDate: String = ""
        get() = "$startDateYear-$startDateMonth-$startDateDayOfMonth"
        private set

    fun refreshDates(rangeFlag: RangeFlag) {
        refreshEndDate()
        refreshStartDate(rangeFlag)
    }

    private fun refreshEndDate() {
        endDateYear = calendar.get(Calendar.YEAR)
        endDateMonth = calendar.get(Calendar.MONTH) + 1
        endDateDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun refreshStartDate(rangeFlag: RangeFlag) {
        when (rangeFlag) {
            RangeFlag.ONE_MONTH -> calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
            RangeFlag.TWO_MONTHS -> calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2)
            RangeFlag.ONE_YEAR -> calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
        }

        startDateYear = calendar.get(Calendar.YEAR)
        startDateMonth = calendar.get(Calendar.MONTH) + 1
        startDateDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    }
}