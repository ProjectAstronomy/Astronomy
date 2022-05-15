package com.project.mrp.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoManifest(
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("landing_date") val landingDate: String?,
    @field:SerializedName("launch_date") val launchDate: String?,
    @field:SerializedName("status") val status: String?,
    @field:SerializedName("max_sol") val maxSol: Long?,
    @field:SerializedName("max_date") val maxDate: String?,
    @field:SerializedName("total_photos") val totalPhotos: Long?,
    @field:SerializedName("photos") val photos: List<PhotosInformation>?
) : Parcelable