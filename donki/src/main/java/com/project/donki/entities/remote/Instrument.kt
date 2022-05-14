package com.project.donki.entities.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Instrument(
    @field:SerializedName("displayName") val displayName: String?
) : Parcelable