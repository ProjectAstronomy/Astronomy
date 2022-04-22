package com.project.donki.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.donki.usecase.SolarFlareUseCase

class SolarFlareViewModelFactory(
    private val solarFlareUseCase: SolarFlareUseCase
) : ViewModelAssistedFactory<SolarFlareViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): SolarFlareViewModel =
        SolarFlareViewModel(savedStateHandle, solarFlareUseCase)
}