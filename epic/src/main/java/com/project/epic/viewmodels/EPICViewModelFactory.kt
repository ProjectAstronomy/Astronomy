package com.project.epic.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.epic.usecases.EPICUseCase

class EPICViewModelFactory(private val epicUseCase: EPICUseCase) :
    ViewModelAssistedFactory<EPICViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): EPICViewModel =
        EPICViewModel(savedStateHandle, epicUseCase)
}