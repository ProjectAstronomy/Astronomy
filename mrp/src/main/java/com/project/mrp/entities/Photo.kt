package com.project.mrp.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    @field:SerializedName("camera") val camera: Camera?,
    @field:SerializedName("earth_date") val earthDate: String?,
    @field:SerializedName("id") val id: Long?,
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("rover") val rover: Rover?,
    @field:SerializedName("sol") val sol: Long?
) : Parcelable