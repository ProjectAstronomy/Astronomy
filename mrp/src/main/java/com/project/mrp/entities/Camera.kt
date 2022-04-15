package com.project.mrp.entities

import com.google.gson.annotations.SerializedName

data class Camera(
    @field:SerializedName("full_name") val fullName: String?,
    @field:SerializedName("id") val id: Long?,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("rover_id") val roverId: Int?
)