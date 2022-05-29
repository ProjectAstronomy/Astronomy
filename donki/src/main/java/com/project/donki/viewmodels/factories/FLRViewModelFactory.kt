package com.project.donki.viewmodels.factories

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.donki.usecases.FLRUseCase
import com.project.donki.viewmodels.FLRViewModel

class FLRViewModelFactory(private val flrUseCase: FLRUseCase) : ViewModelAssistedFactory<FLRViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): FLRViewModel =
        FLRViewModel(savedStateHandle, flrUseCase)
}