package com.project.epic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.epic.domain.EPICRepository
import com.project.epic.entities.EPICResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EPICViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val epicRepository: EPICRepository
) : BaseViewModel() {

    companion object {
        private const val EPIC_RESPONSE = "EPIC_RESPONSE"
        private const val ERROR = "ERROR"
    }

    fun responseEPIC(): LiveData<List<EPICResponse>> = savedStateHandle.getLiveData(EPIC_RESPONSE)

    fun error(): LiveData<Exception> = savedStateHandle.getLiveData(ERROR)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(EPIC_RESPONSE, null)
        savedStateHandle.set(ERROR, null)
    }

    override fun handleThrowable(throwable: Throwable) {
        savedStateHandle.set(ERROR, throwable)
    }

    fun loadAsync() {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<EPICResponse>
            withContext(Dispatchers.IO) {
                result = epicRepository.loadAsync()
            }
            savedStateHandle.set(EPIC_RESPONSE, result)
        }
    }
}