package com.project.donki.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = ALL_KP_INDEX_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = GeomagneticStormEntity::class,
            parentColumns = [GEOMAGNETIC_STORM_ID],
            childColumns = [GEOMAGNETIC_STORM_ID]
        )
    ]
)
data class AllKpIndexEntity(
    @ColumnInfo(name = "observed_time") val observedTime: String?,
    @ColumnInfo(name = "kp_index") val kpIndex: Long?,
    val source: String?,
    @ColumnInfo(name = GEOMAGNETIC_STORM_ID) val gstID: String? = null
)