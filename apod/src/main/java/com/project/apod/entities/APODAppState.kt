package com.project.apod.entities

sealed class APODAppState {
    data class Success(val data: APODResponse?, val list: List<APODResponse>?) : APODAppState()
    data class Error(val errorMessage: String) : APODAppState()
    data class Loading(var progress: Int?) : APODAppState()
}
