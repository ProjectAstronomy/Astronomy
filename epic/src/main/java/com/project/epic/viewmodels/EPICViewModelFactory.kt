package com.project.epic.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.epic.domain.EPICRepository

class EPICViewModelFactory(private val epicRepository: EPICRepository) :
    ViewModelAssistedFactory<EPICViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): EPICViewModel =
        EPICViewModel(savedStateHandle, epicRepository)
}