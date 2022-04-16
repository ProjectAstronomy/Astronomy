package com.project.mrp.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.mrp.domain.Repository

class MRPViewModelFactory(private val repository: Repository) :
    ViewModelAssistedFactory<MRPViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): MRPViewModel =
        MRPViewModel(savedStateHandle, repository)
}