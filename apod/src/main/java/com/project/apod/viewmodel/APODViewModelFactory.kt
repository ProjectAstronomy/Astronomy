package com.project.apod.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.apod.domain.APODRepository
import com.project.core.viewmodel.ViewModelAssistedFactory

class APODViewModelFactory(
    private val apodRepository: APODRepository
) : ViewModelAssistedFactory<APODViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): APODViewModel =
        APODViewModel(savedStateHandle, apodRepository)
}