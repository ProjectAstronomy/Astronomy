package com.project.mrp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.mrp.domain.remote.MissionManifestRepository
import com.project.mrp.entities.remote.MissionManifest
import com.project.mrp.entities.remote.PhotoManifest
import com.project.mrp.entities.remote.PhotosInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MissionManifestViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MissionManifestRepository,
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val MISSION_MANIFEST_RESPONSE = "MISSION_MANIFEST_RESPONSE"
        private const val MISSION_MANIFEST_RESPONSE_LIST = "MISSION_MANIFEST_RESPONSE_LIST"
    }

    fun responseMissionManifest(): LiveData<PhotoManifest> =
        savedStateHandle.getLiveData(MISSION_MANIFEST_RESPONSE)

    fun responseMissionManifestList(): LiveData<List<PhotosInformation>> =
        savedStateHandle.getLiveData(MISSION_MANIFEST_RESPONSE_LIST)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(MISSION_MANIFEST_RESPONSE, null)
    }

    fun loadAsync(roverName: String) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: MissionManifest
            withContext(Dispatchers.IO) {
                result = repository.loadMissionManifest(roverName)
            }
            savedStateHandle.set(MISSION_MANIFEST_RESPONSE, result.photoManifest)
            savedStateHandle.set(MISSION_MANIFEST_RESPONSE_LIST, photoList(result.photoManifest))
        }
    }

    private fun photoList(photos: PhotoManifest?): List<PhotosInformation> {
        val listPhotoDisplay = mutableListOf<PhotosInformation>()
        photos?.photos?.apply {
            for (n in 0..(photos.photos.size - 2000)) {
                listPhotoDisplay += PhotosInformation(
                    get(n).sol, get(n).earthDate,
                    get(n).totalPhotos, get(n).cameras)
            }
        }
        return listPhotoDisplay
    }
}