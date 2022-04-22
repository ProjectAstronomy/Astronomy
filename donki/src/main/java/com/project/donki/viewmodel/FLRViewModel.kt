package com.project.donki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.donki.entities.SolarFlare
import com.project.donki.usecase.FLRUseCase
import kotlinx.coroutines.launch

class FLRViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val flrUseCase: FLRUseCase
) : BaseViewModel() {

    companion object {
        private const val FLR_RESPONSE = "FLR_RESPONSE"
        private const val ERROR = "ERROR"
    }

    fun responseSolarFlare(): LiveData<List<SolarFlare>> = savedStateHandle.getLiveData(FLR_RESPONSE)

    fun error(): LiveData<Exception> = savedStateHandle.getLiveData(ERROR)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(FLR_RESPONSE, null)
        savedStateHandle.set(ERROR, null)
    }

    override fun handleThrowable(throwable: Throwable) {
        savedStateHandle.set(ERROR, throwable)
    }

    override fun loadAsync() {
        cancelJob()
        viewModelCoroutineScope.launch {
            try {
                flrUseCase.loadAsync().apply {
                    reversed()
                    saveLoadedData(this)
                }
            } catch (exception: Exception) {
                savedStateHandle.set(ERROR, exception)
            }
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