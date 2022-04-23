package com.project.mrp.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.mrp.domain.PhotosRepository

class PhotosViewModelFactory(private val photosRepository: PhotosRepository) :
    ViewModelAssistedFactory<PhotosViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): PhotosViewModel =
        PhotosViewModel(savedStateHandle, photosRepository)
}