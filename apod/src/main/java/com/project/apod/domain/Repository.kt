package com.project.apod.domain

import com.project.apod.entities.APODResponse

class Repository(private val webDataSource: WebDataSource) {
    suspend fun getAPODByDate(date: String): retrofit2.Response<APODResponse> =
        webDataSource.getAPODByDate(date)

    suspend fun getAPODFromDateToDate(startDate: String, endDate: String): retrofit2.Response<List<APODResponse>> =
        webDataSource.getAPODFromDateToDate(startDate, endDate)

    suspend fun getAPODByCount(count: Int): retrofit2.Response<List<APODResponse>> =
        webDataSource.getAPODByCount(count)
}