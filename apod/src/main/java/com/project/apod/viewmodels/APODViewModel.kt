package com.project.apod.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.apod.entities.APODResponse
import com.project.apod.usecases.APODUseCase
import com.project.core.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APODViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val apodUseCase: APODUseCase
) : BaseViewModel(savedStateHandle) {

    companion object {
        internal const val APODRESPONSE_FROM_DATE_TO_DATE = "PODRESPONSE_FROM_DATE_TO_DATE"
    }

    fun responseAPODFromDateToDate(): LiveData<List<APODResponse>> {
        return savedStateHandle.getLiveData(APODRESPONSE_FROM_DATE_TO_DATE)
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, null)
    }

    fun load(isNetworkAvailable: Boolean) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<APODResponse>
            withContext(Dispatchers.IO) {
                result = apodUseCase.load(isNetworkAvailable).reversed()
            }
            saveLoadedData(result)
        }
    }

    fun reload() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<APODResponse>
            withContext(Dispatchers.IO) {
                result = apodUseCase.reload().reversed()
            }
            saveLoadedData(result)
        }
    }

    fun insert(apodResponse: APODResponse) {
        viewModelScope.launch { apodUseCase.insert(apodResponse) }
    }

    internal fun saveLoadedData(result: List<APODResponse>) {
        if (!savedStateHandle.contains(APODRESPONSE_FROM_DATE_TO_DATE)) {
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, result)
        } else {
            val list = savedStateHandle
                .getLiveData<List<APODResponse>>(APODRESPONSE_FROM_DATE_TO_DATE)
                .value
                ?.toMutableList()

            list?.addAll(result)
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, list)
        }
    }
}