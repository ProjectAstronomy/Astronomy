package com.project.apod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.apod.domain.APODRepository
import com.project.apod.domain.CalendarRepository
import com.project.apod.entities.APODResponse
import com.project.core.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APODViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val apodRepository: APODRepository,
    private val calendarRepository: CalendarRepository
) : BaseViewModel() {

    companion object {
        private const val APODRESPONSE_FROM_DATE_TO_DATE = "PODRESPONSE_FROM_DATE_TO_DATE"
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle[APODRESPONSE_FROM_DATE_TO_DATE] = null
    }

    fun getAPODFromDateToDate() {
        cancelJob()
        viewModelCoroutineScope.launch {
            val startDate = calendarRepository.getStartDate()
            val endDate = calendarRepository.endDate
            loadAsync(startDate, endDate)
        }
    }

    private suspend fun loadAsync(startDate: String, endDate: String) {
        var result: List<APODResponse>?
        withContext(Dispatchers.IO) {
            result = apodRepository.getAPODFromDateToDate(startDate, endDate)
        }
        savedStateHandle[APODRESPONSE_FROM_DATE_TO_DATE] = result?.reversed()
    }

    fun responseAPODFromDateToDate(): LiveData<List<APODResponse>> =
        savedStateHandle.getLiveData(APODRESPONSE_FROM_DATE_TO_DATE)
}

