package com.project.donki.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllKpIndex(
    @field:SerializedName("observedTime") val observedTime: String?,
    @field:SerializedName("kpIndex") val kpIndex: Long?,
    @field:SerializedName("source") val source: String?
) : Parcelable