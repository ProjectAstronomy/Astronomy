package com.project.mrp.entities

import com.google.gson.annotations.SerializedName

data class MissionManifest(
    @field:SerializedName("photo_manifest") val photoManifest: PhotoManifest?
)