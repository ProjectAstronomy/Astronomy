package com.project.mrp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.BaseViewModel
import com.project.mrp.domain.MissionManifestRepository
import com.project.mrp.entities.MissionManifest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MissionManifestViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val missionManifestRepository: MissionManifestRepository
) : BaseViewModel(savedStateHandle) {

    companion object {
        private const val MISSION_MANIFEST_RESPONSE = "MISSION_MANIFEST_RESPONSE"
    }

    fun responseEPIC(): LiveData<List<MissionManifest>> = savedStateHandle.getLiveData(MISSION_MANIFEST_RESPONSE)

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set(MISSION_MANIFEST_RESPONSE, null)
    }

    fun loadAsync(roverName: String) {
        cancelJob()
        viewModelCoroutineScope.launch {
            var result: MissionManifest
            withContext(Dispatchers.IO) {
                result = missionManifestRepository.loadMissionManifest(roverName)
            }
            savedStateHandle.set(MISSION_MANIFEST_RESPONSE, result)
        }
    }
}