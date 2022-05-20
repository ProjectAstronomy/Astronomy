package com.project.mrp.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotosInformation(
    @field:SerializedName("sol") val sol: Long?,
    @field:SerializedName("earth_date") val earthDate: String?,
    @field:SerializedName("total_photos") val totalPhotos: Long?,
    @field:SerializedName("cameras") val cameras: List<String>?
) : Parcelable