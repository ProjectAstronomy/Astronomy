package com.project.apod.domain

import java.util.*

class CalendarRepository {
    private val calendar = Calendar.getInstance()
    private var year: Int = calendar.get(Calendar.YEAR)
    private var month: Int = calendar.get(Calendar.MONTH)
    private val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)

    val endDate: String = "$year-$month-$dayOfMonth"

    fun getStartDate(): String {
        month -= 1
        if (month <= 0) {
            year -= 1
            month = 12
        }
        return "$year-$month-$dayOfMonth"
    }
}