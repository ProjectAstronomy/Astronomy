package com.project.apod.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.apod.domain.Repository
import com.project.core.viewmodel.ViewModelAssistedFactory

class APODViewModelFactory(
    private val repository: Repository
) : ViewModelAssistedFactory<APODViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): APODViewModel =
        APODViewModel(savedStateHandle, repository)
}