package com.project.donki.entities.local

import androidx.room.*
import com.project.donki.database.ALL_KP_INDEX_TABLE
import com.project.donki.database.GEOMAGNETIC_STORM_ID

@Entity(
    tableName = ALL_KP_INDEX_TABLE,
    indices = [Index(value = [GEOMAGNETIC_STORM_ID])],
    foreignKeys = [
        ForeignKey(
            entity = GeomagneticStormEntity::class,
            parentColumns = [GEOMAGNETIC_STORM_ID],
            childColumns = [GEOMAGNETIC_STORM_ID]
        )
    ]
)
data class AllKpIndexEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "observed_time") val observedTime: String?,
    @ColumnInfo(name = "kp_index") val kpIndex: Long?,
    val source: String?,
    @ColumnInfo(name = GEOMAGNETIC_STORM_ID) val gstID: String? = null
)