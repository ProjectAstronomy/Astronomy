package com.project.epic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.epic.domain.EPICBaseRepository
import com.project.epic.entities.EPICResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EPICViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: EPICBaseRepository
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val EPIC_RESPONSE = "EPIC_RESPONSE"
    }

    fun responseEPIC(): LiveData<List<EPICResponse>> = savedStateHandle.getLiveData(EPIC_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(EPIC_RESPONSE, null)
    }

    fun loadAsync(quality: String = "natural") {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<EPICResponse>
            withContext(Dispatchers.IO) {
                result = repository.loadAsync(quality)
            }
            savedStateHandle.set(EPIC_RESPONSE, result)
        }
    }
}