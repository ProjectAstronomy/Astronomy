package com.project.epic.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.epic.domain.EPICBaseRepository

class EPICViewModelFactory(private val repository: EPICBaseRepository) :
    ViewModelAssistedFactory<EPICViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): EPICViewModel =
        EPICViewModel(savedStateHandle, repository)
}