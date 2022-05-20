package com.project.epic.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CentroidCoordinates(val lat: Double?, val lon: Double?) : Parcelable