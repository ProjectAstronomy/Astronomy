package com.project.apod.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.apod.entities.APODResponse
import com.project.apod.usecases.APODUseCase
import com.project.core.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APODViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val apodUseCase: APODUseCase
) : BaseViewModel() {

    companion object {
        private const val APODRESPONSE_FROM_DATE_TO_DATE = "PODRESPONSE_FROM_DATE_TO_DATE"
        private const val ERROR = "ERROR"
    }

    fun responseAPODFromDateToDate(): LiveData<List<APODResponse>> =
        savedStateHandle.getLiveData(APODRESPONSE_FROM_DATE_TO_DATE)

    fun error(): LiveData<Exception> =
        savedStateHandle.getLiveData(ERROR)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, null)
        savedStateHandle.set(ERROR, null)
    }

    override fun handleThrowable(throwable: Throwable) {
        savedStateHandle.set(ERROR, throwable)
    }

    fun loadAsync() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<APODResponse>
            withContext(Dispatchers.IO) {
                result = apodUseCase.loadAsync().reversed()
            }
            saveLoadedData(result)
        }
    }

    private fun saveLoadedData(result: List<APODResponse>) {
        if (!savedStateHandle.contains(APODRESPONSE_FROM_DATE_TO_DATE)) {
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, result)
        } else {
            val list = savedStateHandle.getLiveData<List<APODResponse>>(APODRESPONSE_FROM_DATE_TO_DATE).value?.toMutableList()
            list?.removeLast()
            list?.addAll(result)
            savedStateHandle.set(APODRESPONSE_FROM_DATE_TO_DATE, list)
        }
    }
}

