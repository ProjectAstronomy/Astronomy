package com.project.donki.entities.local

import androidx.room.*
import com.project.donki.database.GEOMAGNETIC_STORM_ID
import com.project.donki.database.LINKED_EVENT_TABLE
import com.project.donki.database.SOLAR_FLARE_ID

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
    ]
)
data class LinkedEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "activity_id") val activityID: String?,
    @ColumnInfo(name = SOLAR_FLARE_ID, index = true) val flrID: String? = null,
    @ColumnInfo(name = GEOMAGNETIC_STORM_ID, index = true) val gstID: String? = null
)