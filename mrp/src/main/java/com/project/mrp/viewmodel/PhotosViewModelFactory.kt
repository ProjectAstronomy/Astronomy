package com.project.mrp.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.mrp.domain.BasePhotosRepository

class PhotosViewModelFactory(private val repository: BasePhotosRepository) :
    ViewModelAssistedFactory<PhotosViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): PhotosViewModel =
        PhotosViewModel(savedStateHandle, repository)
}