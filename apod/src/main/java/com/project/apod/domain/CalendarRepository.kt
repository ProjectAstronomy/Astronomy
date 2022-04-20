package com.project.apod.domain

import java.util.*

class CalendarRepository {
    private val calendar = Calendar.getInstance()

    private var endDateYear: Int = calendar.get(Calendar.YEAR)
    private var endDateMonth: Int = calendar.get(Calendar.MONTH)
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

    fun refreshEndDate() {
        endDateYear = startDateYear
        endDateMonth = startDateMonth
        endDateDayOfMonth = startDateDayOfMonth
    }

    fun refreshStartDate() {
        startDateMonth -= 1
        if (startDateMonth <= 0) {
            startDateYear -= 1
            startDateMonth = 12
        }
    }
}