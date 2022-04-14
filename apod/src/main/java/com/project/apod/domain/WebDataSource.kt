package com.project.apod.domain

import com.project.apod.BuildConfig
import com.project.apod.entities.APODResponse
import com.project.core.domain.BaseWebDataSource

class WebDataSource : BaseWebDataSource() {
    private val apodApiService = retrofit.create(APODApiService::class.java)

    suspend fun getAPODByDate(date: String): retrofit2.Response<APODResponse> =
        apodApiService.getAPODByDate(date, BuildConfig.DEMO_KEY)

    suspend fun getAPODFromDateToDate(
        startDate: String,
        endDate: String
    ): retrofit2.Response<List<APODResponse>> =
        apodApiService.getAPODFromDateToDate(startDate, endDate, BuildConfig.DEMO_KEY)

    suspend fun getAPODByCount(count: Int): retrofit2.Response<List<APODResponse>> =
        apodApiService.getAPODByCount(count, BuildConfig.DEMO_KEY)
}