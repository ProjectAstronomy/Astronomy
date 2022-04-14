package com.project.apod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.apod.domain.Repository
import com.project.apod.entities.APODAppState
import com.project.apod.entities.APODResponse
import kotlinx.coroutines.launch

class APODViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : BaseViewModel() {

    companion object {
        private const val LIVE_DATA_PODRESPONSE_BY_DATE_TAG = "LIVE_DATA_PODRESPONSE_TAG"
        private const val LIVE_DATA_PODRESPONSE_FROM_DATE_TO_DATE_TAG = "LIVE_DATA_LIST_OF_PODRESPONSE_TAG"
        private const val LIVE_DATA_PODRESPONSE_BY_COUNT_TAG = "LIVE_DATA_LIST_OF_PODRESPONSE_BY_COUNT_TAG"
    }

    fun getAPODByDate(date: String) {
        savedStateHandle[LIVE_DATA_PODRESPONSE_BY_DATE_TAG] = null
        cancelJob()
        viewModelCoroutineScope.launch {
            val response = repository.getAPODByDate(date)
            if (response.isSuccessful) {
                savedStateHandle[LIVE_DATA_PODRESPONSE_BY_DATE_TAG] =
                    response.body()?.let { APODAppState.Success(it, null) }
            } else {
                //TODO: handle error
            }
        }
    }


    fun getAPODFromDateToDate(startDate: String, endDate: String) {
        savedStateHandle[LIVE_DATA_PODRESPONSE_FROM_DATE_TO_DATE_TAG] = null
        cancelJob()
        viewModelCoroutineScope.launch {
            val response = repository.getAPODFromDateToDate(startDate, endDate)
            if (response.isSuccessful) {
                savedStateHandle[LIVE_DATA_PODRESPONSE_BY_DATE_TAG] =
                    response.body()?.let { APODAppState.Success(null, it) }
            } else {
                //TODO: handle error
            }
        }
    }

    fun getAPODByCount(count: Int) {
        savedStateHandle[LIVE_DATA_PODRESPONSE_FROM_DATE_TO_DATE_TAG] = null
        cancelJob()
        viewModelCoroutineScope.launch {
            val response = repository.getAPODByCount(count)
            if (response.isSuccessful) {
                savedStateHandle[LIVE_DATA_PODRESPONSE_BY_DATE_TAG] =
                    response.body()?.let { APODAppState.Success(null, it) }
            } else {
                //TODO: handle error
            }
        }
    }

    fun responseAPODByDate(): LiveData<APODResponse>? = savedStateHandle[LIVE_DATA_PODRESPONSE_BY_DATE_TAG]

    fun responseAPODFromDateToDate(): LiveData<APODResponse>? = savedStateHandle[LIVE_DATA_PODRESPONSE_FROM_DATE_TO_DATE_TAG]

    fun responseAPODByCount(): LiveData<APODResponse>? = savedStateHandle[LIVE_DATA_PODRESPONSE_BY_COUNT_TAG]
}