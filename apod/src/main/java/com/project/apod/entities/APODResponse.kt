package com.project.apod.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class APODResponse(
    @field:SerializedName("copyright") val copyright: String?,
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("hdurl") val hdurl: String?,
    @field:SerializedName("mediaType") val mediaType: String?,
    @field:SerializedName("serviceVersion") val serviceVersion: String?,
    @field:SerializedName("title") val title: String?
) : Parcelable