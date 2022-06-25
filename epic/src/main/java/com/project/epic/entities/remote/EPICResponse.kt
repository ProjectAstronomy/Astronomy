package com.project.epic.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EPICResponse(
    @field:SerializedName("identifier") val identifier: String,
    @field:SerializedName("caption") val caption: String?,
    @field:SerializedName("image") val image: String?,
    @field:SerializedName("version") val version: String?,
    @field:SerializedName("centroid_coordinates") val centroidCoordinates: CentroidCoordinates?,
    @field:SerializedName("dscovr_j2000_position") val dscovrJ2000Position: J2000Position?,
    @field:SerializedName("lunar_j2000_position") val lunarJ2000Position: J2000Position?,
    @field:SerializedName("sun_j2000_position") val sunJ2000Position: J2000Position?,
    @field:SerializedName("attitude_quaternions") val attitudeQuaternions: AttitudeQuaternions?,
    @field:SerializedName("date") val date: String?
) : Parcelable