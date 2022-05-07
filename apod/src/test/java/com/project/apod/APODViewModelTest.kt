package com.project.apod

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.project.apod.entities.APODResponse
import com.project.apod.usecases.APODUseCase
import com.project.apod.viewmodels.APODViewModel
import com.project.apod.viewmodels.APODViewModel.Companion.APODRESPONSE_FROM_DATE_TO_DATE
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class APODViewModelTest {
    private val isDateNeededToBeRefreshed = true
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
    private val apodUseCase = mockk<APODUseCase> {
        coEvery { loadAsync(isDateNeededToBeRefreshed) } returns provideList()
    }
    private val apodViewModel = APODViewModel(savedStateHandle, apodUseCase)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = TestCoroutineDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test loadAsync()`() {
        apodViewModel.loadAsync(isDateNeededToBeRefreshed)
        coVerify(atLeast = 1) { apodUseCase.loadAsync(isDateNeededToBeRefreshed) }
    }

    @Test
    fun `test saveLoadedData() if savedStateHandle is empty`() {
        every { savedStateHandle.contains(APODRESPONSE_FROM_DATE_TO_DATE) } returns false
        apodViewModel.saveLoadedData(provideList())
        verify { savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, provideList()) }
    }

    @Test
    fun `test saveLoadedData() if savedStateHandle contains data`() {
        val observer = Observer<List<APODResponse>> {

        }
        val liveData = apodViewModel.responseAPODFromDateToDate()

        try {
            liveData.observeForever(observer)
            apodViewModel.saveLoadedData(provideList())
            assertNotNull(liveData.value)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun finish() {
        Dispatchers.resetMain()
    }

    private fun provideList(): List<APODResponse> {
        val list = mutableListOf<APODResponse>()
        for (i in 0..9) {
            list.add(
                APODResponse(
                    copyright = "[$i] copyright",
                    date = "[$i] date",
                    explanation = "[$i] explanation",
                    hdurl = "[$i] hdurl",
                    mediaType = "[$i] mediaType",
                    serviceVersion = "[$i] serviceVersion",
                    title = "[$i] title",
                    url = "[$i] url"
                )
            )
        }
        return list
    }
}