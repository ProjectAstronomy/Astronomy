package com.project.donki.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.donki.entities.SolarFlare
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

    fun loadAsync() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<SolarFlare>
            withContext(Dispatchers.IO) {
                result = flrUseCase.loadAsync().reversed()
            }
            saveLoadedData(result)
        }
    }

    private fun saveLoadedData(result: List<SolarFlare>) {
        if (!savedStateHandle.contains(FLR_RESPONSE)) {
            savedStateHandle.set(FLR_RESPONSE, result)
        } else {
            val list =
                savedStateHandle.getLiveData<List<SolarFlare>>(
                    FLR_RESPONSE
                ).value?.toMutableList()
            list?.removeLast()
            list?.addAll(result)
            savedStateHandle.set(FLR_RESPONSE, list)
        }
    }
}