package com.project.apod.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.apod.usecase.APODUseCase
import com.project.core.viewmodel.ViewModelAssistedFactory

class APODViewModelFactory(
    private val apodUseCase: APODUseCase
) : ViewModelAssistedFactory<APODViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): APODViewModel =
        APODViewModel(savedStateHandle, apodUseCase)
}