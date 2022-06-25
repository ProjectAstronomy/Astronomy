package com.project.mrp.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.mrp.domain.remote.MissionManifestRepository

class MissionManifestViewModelFactory(private val repository: MissionManifestRepository) :
    ViewModelAssistedFactory<MissionManifestViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): MissionManifestViewModel =
        MissionManifestViewModel(savedStateHandle, repository)
}