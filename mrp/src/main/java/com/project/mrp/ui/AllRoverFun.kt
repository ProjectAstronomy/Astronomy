package com.project.mrp.ui

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object AllRoverFun {

    @SuppressLint("SimpleDateFormat")
    fun transformDate(date: String?): String {
        val serverDate = date
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val targetFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val date: Date = originalFormat.parse(serverDate.toString()) as Date
        return targetFormat.format(date)
    }
}