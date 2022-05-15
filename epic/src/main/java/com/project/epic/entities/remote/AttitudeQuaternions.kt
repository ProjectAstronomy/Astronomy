package com.project.epic.entities.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttitudeQuaternions(val q0: Double?, val q1: Double?, val q2: Double?, val q3: Double?) : Parcelable