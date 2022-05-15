package com.project.epic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.project.core.viewmodel.BaseViewModel
import com.project.epic.entities.remote.EPICResponse
import com.project.epic.usecases.EPICUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EPICViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val epicUseCase: EPICUseCase
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val EPIC_RESPONSE = "EPIC_RESPONSE"
    }

    fun responseEPIC(): LiveData<List<EPICResponse>> = savedStateHandle.getLiveData(EPIC_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(EPIC_RESPONSE, null)
    }

    fun loadAsync(isNetworkAvailable: Boolean, quality: String = "natural") {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<EPICResponse>
            withContext(Dispatchers.IO) {
                result = epicUseCase.load(isNetworkAvailable, quality)
            }
            savedStateHandle.set(EPIC_RESPONSE, result)
        }
    }

    fun insert(epicResponse: EPICResponse) {
        viewModelScope.launch { epicUseCase.insert(epicResponse) }
    }
}