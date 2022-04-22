package com.project.donki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.donki.entities.SolarFlareResponse
import com.project.donki.usecase.SolarFlareUseCase
import kotlinx.coroutines.launch

class SolarFlareViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val solarFlareUseCase: SolarFlareUseCase
) : BaseViewModel() {

    companion object {
        private const val SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE =
            "SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE"
        private const val ERROR = "ERROR"
    }

    fun responseSolarFlareFromDateToDate(): LiveData<List<SolarFlareResponse>> =
        savedStateHandle.getLiveData(SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE)

    fun error(): LiveData<Exception> =
        savedStateHandle.getLiveData(ERROR)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE, null)
        savedStateHandle.set(ERROR, null)
    }

    override fun handleThrowable(throwable: Throwable) {
        savedStateHandle.set(ERROR, throwable)
    }

    override fun loadAsync() {
        cancelJob()
        viewModelCoroutineScope.launch {
            try {
                solarFlareUseCase.loadAsync().apply {
                    reversed()
                    saveLoadedData(this)
                }
            } catch (exception: Exception) {
                savedStateHandle.set(ERROR, exception)
            }
        }
    }

    private fun saveLoadedData(result: List<SolarFlareResponse>) {
        if (!savedStateHandle.contains(SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE)) {
            savedStateHandle.set(SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE, result)
        } else {
            val list =
                savedStateHandle.getLiveData<List<SolarFlareResponse>>(
                    SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE
                ).value?.toMutableList()
            list?.removeLast()
            list?.addAll(result)
            savedStateHandle.set(SOLAR_FLARE_RESPONSE_FROM_DATE_TO_DATE, list)
        }
    }
}