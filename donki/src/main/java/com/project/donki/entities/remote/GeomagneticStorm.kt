package com.project.donki.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeomagneticStorm(
    @field:SerializedName("gstID") val gstID: String,
    @field:SerializedName("startTime") val startTime: String?,
    @field:SerializedName("allKpIndex") val allKpIndex: List<AllKpIndex>?,
    @field:SerializedName("linkedEvents") val linkedEvents: List<LinkedEvent>?,
    @field:SerializedName("link") var link: String?
) : Parcelable