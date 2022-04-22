package com.project.donki.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.donki.usecase.FLRUseCase

class FLRViewModelFactory(private val flrUseCase: FLRUseCase) : ViewModelAssistedFactory<FLRViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): FLRViewModel =
        FLRViewModel(savedStateHandle, flrUseCase)
}