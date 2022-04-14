package com.project.apod.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.apod.domain.Repository

class APODViewModelFactory(
    private val repository: Repository
) : ViewModelAssistedFactory<APODViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): APODViewModel =
        APODViewModel(savedStateHandle, repository)
}