package com.project.donki.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinkedEvent(
    @field:SerializedName("activityID") val activityID: String?
) : Parcelable