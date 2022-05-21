package com.project.mrp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.mrp.domain.remote.PhotosRepository
import com.project.mrp.entities.remote.Photos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotosViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PhotosRepository,
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val PHOTOS_RESPONSE = "PHOTOS_RESPONSE"
    }

    fun responseManifest(): LiveData<List<Photos>> = savedStateHandle.getLiveData(PHOTOS_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(PHOTOS_RESPONSE, null)
    }

    fun loadAsync(roverName: String = "Spirit", sol: Long = 1) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: Photos
            withContext(Dispatchers.IO) {
                result = repository.loadPhotosByMartianSol(roverName, sol)
            }
            savedStateHandle.set(PHOTOS_RESPONSE, result)
        }
    }
}