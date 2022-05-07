package com.project.apod

import com.project.apod.domain.APODApiService
import com.project.apod.domain.APODRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.atLeast
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class APODRepositoryTest {
    private val apodApiService = mock<APODApiService>()
    private val apodRepository = APODRepository(apodApiService)

    @Test
    fun testLoadAsync(): Unit = runBlocking {
        val startDate = "2022-4-6"
        val endDate = "2022-5-6"
        apodRepository.loadAsync(startDate, endDate)
        verify(apodApiService, atLeast(1)).getAPODFromDateToDate(startDate, endDate)
    }
}