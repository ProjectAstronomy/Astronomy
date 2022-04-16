package com.project.mrp.entities

sealed class PhotoManifestAppState {
    data class Success(val data: PhotoManifest) : PhotoManifestAppState()
    data class Error(val errorMessage: String) : PhotoManifestAppState()
    data class Loading(val progress: Int?) : PhotoManifestAppState()
}