package com.project.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    companion object {
        private const val ERROR = "ERROR"
    }

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
            + SupervisorJob()
            + CoroutineExceptionHandler { _, throwable -> handleThrowable(throwable) })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
        savedStateHandle.set(ERROR, null)
    }

    fun error(): LiveData<Exception> = savedStateHandle.getLiveData(ERROR)

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    private fun handleThrowable(throwable: Throwable) {
        savedStateHandle.set(ERROR, throwable)
    }
}