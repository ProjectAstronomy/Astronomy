package com.project.astronomy.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.astronomy.repository.MainRepository
import com.project.core.viewmodel.ViewModelAssistedFactory

class MainViewModelFactory(private val mainRepository: MainRepository) :
    ViewModelAssistedFactory<MainViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): MainViewModel =
        MainViewModel(savedStateHandle, mainRepository)
}