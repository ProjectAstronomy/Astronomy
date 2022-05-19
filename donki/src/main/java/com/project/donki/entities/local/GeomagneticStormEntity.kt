package com.project.donki.entities.local

import androidx.room.*
import com.project.donki.database.GEOMAGNETIC_STORM_ID
import com.project.donki.database.GEOMAGNETIC_STORM_TABLE

@Entity(
    tableName = GEOMAGNETIC_STORM_TABLE,
    indices = [Index(value = [GEOMAGNETIC_STORM_ID], unique = true)]
)
data class GeomagneticStormEntity(
    @PrimaryKey @ColumnInfo(name = GEOMAGNETIC_STORM_ID) val gstID: String,
    @ColumnInfo(name = "start_time") val startTime: String?,
    val link: String?
) {
    @Ignore var allKpIndex: List<AllKpIndexEntity>? = null
    @Ignore var linkedEvents: List<LinkedEventEntity>? = null
}