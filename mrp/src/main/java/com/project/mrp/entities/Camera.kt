package com.project.mrp.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Camera(
    @field:SerializedName("full_name") val fullName: String?,
    @field:SerializedName("id") val id: Long?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("rover_id") val roverId: Int?
) : Parcelable