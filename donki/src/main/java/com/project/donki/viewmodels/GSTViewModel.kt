package com.project.donki.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.donki.entities.GeomagneticStorm
import com.project.donki.usecases.GSTUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GSTViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val gstUseCase: GSTUseCase
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val GST_RESPONSE = "GST_RESPONSE"
    }

    fun responseGeomagneticStorms(): LiveData<List<GeomagneticStorm>> = savedStateHandle.getLiveData(GST_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(GST_RESPONSE, null)
    }

    fun loadAsync(isDateNeededToBeRefreshed: Boolean) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: List<GeomagneticStorm>
            withContext(Dispatchers.IO) {
                result = gstUseCase.loadAsync(isDateNeededToBeRefreshed).reversed()
            }
            saveLoadedData(result)
        }
    }

    private fun saveLoadedData(result: List<GeomagneticStorm>) {
        if (!savedStateHandle.contains(GST_RESPONSE)) {
            savedStateHandle.set(GST_RESPONSE, result)
        } else {
            val list =
                savedStateHandle.getLiveData<List<GeomagneticStorm>>(GST_RESPONSE).value?.toMutableList()

            list?.addAll(result)
            savedStateHandle.set(GST_RESPONSE, list)
        }
    }
}