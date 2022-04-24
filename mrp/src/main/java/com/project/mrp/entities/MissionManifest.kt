package com.project.mrp.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionManifest(
    @field:SerializedName("photo_manifest") val photoManifest: PhotoManifest?
) : Parcelable