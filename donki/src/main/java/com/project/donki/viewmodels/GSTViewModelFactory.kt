package com.project.donki.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.donki.usecases.GSTUseCase

class GSTViewModelFactory(private val gstUseCase: GSTUseCase) : ViewModelAssistedFactory<GSTViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): GSTViewModel =
        GSTViewModel(savedStateHandle, gstUseCase)
}