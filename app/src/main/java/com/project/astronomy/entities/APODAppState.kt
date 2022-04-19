package com.project.astronomy.entities

import android.os.Parcelable
import com.project.apod.entities.APODResponse
import kotlinx.parcelize.Parcelize

sealed class APODAppState : Parcelable {
    @Parcelize data class Success(val list: APODResponse?) : APODAppState()
    @Parcelize data class Error(val errorMessage: String) : APODAppState()
    @Parcelize data class Loading(var progress: Int?) : APODAppState()
}