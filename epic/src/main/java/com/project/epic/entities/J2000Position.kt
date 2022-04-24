package com.project.epic.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class J2000Position(
    @field:SerializedName("x") val x: Double?,
    @field:SerializedName("y") val y: Double?,
    @field:SerializedName("z") val z: Double?
) : Parcelable