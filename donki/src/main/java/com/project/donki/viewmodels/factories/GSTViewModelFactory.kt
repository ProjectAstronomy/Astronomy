package com.project.donki.viewmodels.factories

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.donki.usecases.GSTUseCase
import com.project.donki.viewmodels.GSTViewModel

class GSTViewModelFactory(private val gstUseCase: GSTUseCase) : ViewModelAssistedFactory<GSTViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): GSTViewModel =
        GSTViewModel(savedStateHandle, gstUseCase)
}