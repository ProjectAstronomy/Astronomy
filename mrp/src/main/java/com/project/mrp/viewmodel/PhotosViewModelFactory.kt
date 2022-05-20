package com.project.mrp.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.project.core.viewmodel.ViewModelAssistedFactory
import com.project.mrp.domain.remote.PhotosRepository

class PhotosViewModelFactory(private val repository: PhotosRepository) :
    ViewModelAssistedFactory<PhotosViewModel> {

    override fun create(savedStateHandle: SavedStateHandle): PhotosViewModel =
        PhotosViewModel(savedStateHandle, repository)
}