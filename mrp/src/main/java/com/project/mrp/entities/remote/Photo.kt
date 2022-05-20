package com.project.mrp.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("sol") val sol: Long?,
    @field:SerializedName("camera") val camera: Camera?,
    @field:SerializedName("img_src") val imgSrc: String?,
    @field:SerializedName("earth_date") val earthDate: String?,
    @field:SerializedName("rover") val rover: Rover?,
) : Parcelable