package com.project.donki.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SolarFlare(
    @field:SerializedName("flrID") val flrID: String,
    @field:SerializedName("instruments") val instruments: List<Instrument>?,
    @field:SerializedName("beginTime") val beginTime: String?,
    @field:SerializedName("peakTime") val peakTime: String?,
    @field:SerializedName("endTime") val endTime: String?,
    @field:SerializedName("classType") val classType: String?,
    @field:SerializedName("sourceLocation") val sourceLocation: String?,
    @field:SerializedName("activeRegionNum") val activeRegionNum: Long?,
    @field:SerializedName("linkedEvents") val linkedEvents: List<LinkedEvent>?,
    @field:SerializedName("link") var link: String?
) : Parcelable