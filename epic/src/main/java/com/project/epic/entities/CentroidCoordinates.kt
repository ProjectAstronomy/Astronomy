package com.project.epic.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CentroidCoordinates(
    @field:SerializedName("lat") val lat: Double?,
    @field:SerializedName("lon") val lon: Double?
) : Parcelable