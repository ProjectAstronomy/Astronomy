package com.project.epic.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class J2000Position(val x: Double?, val y: Double?, val z: Double?) : Parcelable