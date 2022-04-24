package com.project.epic.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttitudeQuaternions(
    @field:SerializedName("q0") val q0: Double?,
    @field:SerializedName("q1") val q1: Double?,
    @field:SerializedName("q2") val q2: Double?,
    @field:SerializedName("q3") val q3: Double?
) : Parcelable