package com.project.mrp.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rover(
    @field:SerializedName("id") val id: Long?,
    @field:SerializedName("landing_date") val landingDate: String?,
    @field:SerializedName("launch_date") val launchDate: String?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("status") val status: String?
) : Parcelable