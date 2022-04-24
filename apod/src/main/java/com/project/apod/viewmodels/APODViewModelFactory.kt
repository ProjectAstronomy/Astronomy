package com.project.apod.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.project.apod.usecases.APODUseCase
import com.project.core.viewmodel.ViewModelAssistedFactory

class APODViewModelFactory(
    private val apodUseCase: APODUseCase
) : ViewModelAssistedFactory<APODViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): APODViewModel =
        APODViewModel(savedStateHandle, apodUseCase)
}