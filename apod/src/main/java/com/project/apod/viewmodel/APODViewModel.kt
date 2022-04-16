package com.project.apod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.apod.domain.APODRepository
import com.project.apod.entities.APODAppState
import com.project.apod.entities.APODResponse
import com.project.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class APODViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val apodRepository: APODRepository,
) : BaseViewModel() {

    companion object {
        private const val PODRESPONSE_BY_DATE = "PODRESPONSE_BY_DATE"
        private const val PODRESPONSE_FROM_DATE_TO_DATE = "PODRESPONSE_FROM_DATE_TO_DATE"
        private const val PODRESPONSE_BY_COUNT = "PODRESPONSE_BY_COUNT"
    }

    fun getAPODByDate(date: String) {
        savedStateHandle[PODRESPONSE_BY_DATE] = null
        cancelJob()
        viewModelCoroutineScope.launch {
            val response = apodRepository.getAPODByDate(date)
            if (response.isSuccessful) {
                savedStateHandle[PODRESPONSE_BY_DATE] =
                    response.body()?.let { APODAppState.Success(it, null) }
            } else {
                //TODO: handle error
            }
        }
    }

    fun getAPODFromDateToDate(startDate: String, endDate: String) {
        savedStateHandle[PODRESPONSE_FROM_DATE_TO_DATE] = null
        cancelJob()
        viewModelCoroutineScope.launch {
            val response = apodRepository.getAPODFromDateToDate(startDate, endDate)
            if (response.isSuccessful) {
                savedStateHandle[PODRESPONSE_BY_DATE] =
                    response.body()?.let { APODAppState.Success(null, it) }
            } else {
                //TODO: handle error
            }
        }
    }

    fun getAPODByCount(count: Int) {
        savedStateHandle[PODRESPONSE_FROM_DATE_TO_DATE] = null
        cancelJob()
        viewModelCoroutineScope.launch {
            val response = apodRepository.getAPODByCount(count)
            if (response.isSuccessful) {
                savedStateHandle[PODRESPONSE_BY_DATE] =
                    response.body()?.let { APODAppState.Success(null, it) }
            } else {
                //TODO: handle error
            }
        }
    }

    fun responseAPODByDate(): LiveData<APODResponse>? = savedStateHandle[PODRESPONSE_BY_DATE]

    fun responseAPODFromDateToDate(): LiveData<APODResponse>? = savedStateHandle[PODRESPONSE_FROM_DATE_TO_DATE]

    fun responseAPODByCount(): LiveData<APODResponse>? = savedStateHandle[PODRESPONSE_BY_COUNT]
}