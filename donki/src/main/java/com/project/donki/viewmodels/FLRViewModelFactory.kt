package com.project.donki.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.donki.usecases.FLRUseCase

class FLRViewModelFactory(private val flrUseCase: FLRUseCase) : ViewModelAssistedFactory<FLRViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): FLRViewModel =
        FLRViewModel(savedStateHandle, flrUseCase)
}