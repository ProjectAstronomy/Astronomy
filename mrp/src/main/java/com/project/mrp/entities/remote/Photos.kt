package com.project.mrp.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photos(
    @field:SerializedName("photos") val photos: List<Photo>?
) : Parcelable
