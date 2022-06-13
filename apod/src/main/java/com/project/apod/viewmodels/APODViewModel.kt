package com.project.apod.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.apod.entities.remote.APODResponse
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
        internal const val IS_ONCE_CREATED = "IS_ONCE_CREATED"
    }

    init {
        load()
    }

    fun responseAPODFromDateToDate(): LiveData<List<APODResponse>> {
        return savedStateHandle.getLiveData(APODRESPONSE_FROM_DATE_TO_DATE)
    }

    fun isOnceCreated(): LiveData<Boolean> {
        return savedStateHandle.getLiveData(IS_ONCE_CREATED)
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, null)
    }

    private fun load() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<APODResponse>
            withContext(Dispatchers.IO) {
                result = apodUseCase.load().reversed()
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

    fun onLoadingFinished() {
        savedStateHandle.set(IS_ONCE_CREATED, true)
    }

    internal fun saveLoadedData(result: List<APODResponse>) {
        if (!savedStateHandle.contains(APODRESPONSE_FROM_DATE_TO_DATE)) {
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, result)
        } else {
            val list = savedStateHandle
                .getLiveData<List<APODResponse>>(APODRESPONSE_FROM_DATE_TO_DATE).value?.toMutableList()
            list?.removeLast()
            list?.addAll(result)
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, list)
        }
    }
}