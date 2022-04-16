package com.project.mrp.entities

sealed class PhotosAppState {
    data class Success(val data: List<Photo>) : PhotosAppState()
    data class Error(val errorMessage: String) : PhotosAppState()
    data class Loading(val progress: Int?) : PhotosAppState()
}