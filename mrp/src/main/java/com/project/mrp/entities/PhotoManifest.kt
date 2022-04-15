package com.project.mrp.entities

import com.google.gson.annotations.SerializedName

data class PhotoManifest(
    @field:SerializedName("landing_date") val landingDate: String?,
    @field:SerializedName("launch_date") val launchDate: String?,
    @field:SerializedName("max_date") val maxDate: String?,
    @field:SerializedName("max_sol") val maxSol: Long?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("status") val status: String?,
    @field:SerializedName("total_photos") val totalPhotos: Long?
)