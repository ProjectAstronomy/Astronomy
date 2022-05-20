package com.project.donki.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.core.viewmodel.BaseViewModel
import com.project.donki.entities.remote.SolarFlare
import com.project.donki.usecases.FLRUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FLRViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val flrUseCase: FLRUseCase
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val FLR_RESPONSE = "FLR_RESPONSE"
    }

    fun responseSolarFlare(): LiveData<List<SolarFlare>> = savedStateHandle.getLiveData(FLR_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(FLR_RESPONSE, null)
    }

    fun load(isNetworkAvailable: Boolean) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<SolarFlare>
            withContext(Dispatchers.IO) {
                result = flrUseCase.load(isNetworkAvailable).reversed()
            }
            saveLoadedData(result)
        }
    }

    fun reload() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<SolarFlare>
            withContext(Dispatchers.IO) {
                result = flrUseCase.reload().reversed()
            }
            saveLoadedData(result)
        }
    }

    fun insert(solarFlare: SolarFlare) {
        viewModelScope.launch { flrUseCase.insert(solarFlare) }
    }

    private fun saveLoadedData(result: List<SolarFlare>) {
        if (!savedStateHandle.contains(FLR_RESPONSE)) {
            savedStateHandle.set(FLR_RESPONSE, result)
        } else {
            val list =
                savedStateHandle.getLiveData<List<SolarFlare>>(FLR_RESPONSE).value?.toMutableList()
            list?.addAll(result)
            savedStateHandle.set(FLR_RESPONSE, list)
        }
    }
}