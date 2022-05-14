package com.project.donki.entities.local

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = GEOMAGNETIC_STORM_TABLE,
    indices = [Index(value = [GEOMAGNETIC_STORM_ID], unique = true)]
)
data class GeomagneticStormEntity(
    @PrimaryKey @ColumnInfo(name = GEOMAGNETIC_STORM_ID) val gstID: String,
    @ColumnInfo(name = "start_time") val startTime: String?,
    @Ignore var allKpIndex: List<AllKpIndexEntity>? = null,
    @Ignore var linkedEvents: List<LinkedEventEntity>? = null,
    val link: String?
) : Parcelable