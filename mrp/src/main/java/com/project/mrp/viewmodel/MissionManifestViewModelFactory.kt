package com.project.mrp.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.mrp.domain.BaseMissionManifestRepository

class MissionManifestViewModelFactory(private val repository: BaseMissionManifestRepository) :
    ViewModelAssistedFactory<MissionManifestViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): MissionManifestViewModel =
        MissionManifestViewModel(savedStateHandle, repository)
}