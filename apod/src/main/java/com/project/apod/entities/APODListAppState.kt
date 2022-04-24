package com.project.apod.entities

sealed class APODListAppState {
    data class Success(val list: List<APODResponse>?) : APODListAppState()
    data class Error(val errorMessage: String) : APODListAppState()
    data class Loading(var progress: Int?) : APODListAppState()
}