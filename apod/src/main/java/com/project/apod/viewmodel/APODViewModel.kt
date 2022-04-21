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
        private const val ERROR = "ERROR"
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle[APODRESPONSE_FROM_DATE_TO_DATE] = null
    }

    fun uploadAPODFromDateToDate() {
        cancelJob()
        viewModelCoroutineScope.launch {
            with(calendarRepository) {
                refreshEndDate(); refreshStartDate()
                loadAsync(startDate, endDate)
            }
        }
    }

    private suspend fun loadAsync(startDate: String, endDate: String) {
        try {
            var result: List<APODResponse>?
            withContext(Dispatchers.IO) {
                result = apodRepository.getAPODFromDateToDate(startDate, endDate).reversed()
            }
            saveLoadedData(result)
        } catch (exception: Exception) {
            savedStateHandle.set(ERROR, exception)
        }
    }

    private fun saveLoadedData(result: List<APODResponse>?) {
        if (!savedStateHandle.contains(APODRESPONSE_FROM_DATE_TO_DATE)) {
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, result)
        } else {
            val list =
                savedStateHandle.getLiveData<List<APODResponse>>(APODRESPONSE_FROM_DATE_TO_DATE).value?.toMutableList()
            list?.removeLast()
            result?.let { list?.addAll(it) }
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, list)
        }
    }

    fun responseAPODFromDateToDate(): LiveData<List<APODResponse>> =
        savedStateHandle.getLiveData(APODRESPONSE_FROM_DATE_TO_DATE)

    fun error(): LiveData<Exception> =
        savedStateHandle.getLiveData(ERROR)
}

