package com.project.donki.entities.local

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = LINKED_EVENT_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = SolarFlareEntity::class,
            parentColumns = [SOLAR_FLARE_ID],
            childColumns = [SOLAR_FLARE_ID]
        ),
        ForeignKey(
            entity = GeomagneticStormEntity::class,
            parentColumns = [GEOMAGNETIC_STORM_ID],
            childColumns = [GEOMAGNETIC_STORM_ID]
        )
    ],
    indices = [Index(value = [SOLAR_FLARE_ID, GEOMAGNETIC_STORM_ID])]
)
data class LinkedEventEntity(
    @ColumnInfo(name = "activity_id") val activityID: String?,
    @ColumnInfo(name = SOLAR_FLARE_ID) val flrID: String? = null,
    @ColumnInfo(name = GEOMAGNETIC_STORM_ID) val gstID: String? = null
) : Parcelable