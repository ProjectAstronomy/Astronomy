package com.project.donki.entities.local

import androidx.room.*
import com.project.donki.database.SOLAR_FLARE_ID
import com.project.donki.database.SOLAR_FLARE_TABLE

@Entity(tableName = SOLAR_FLARE_TABLE, indices = [Index(value = [SOLAR_FLARE_ID], unique = true)])
data class SolarFlareEntity(
    @PrimaryKey @ColumnInfo(name = SOLAR_FLARE_ID) val flrID: String,
    @ColumnInfo(name = "begin_time") val beginTime: String?,
    @ColumnInfo(name = "peak_time") val peakTime: String?,
    @ColumnInfo(name = "end_time") val endTime: String?,
    @ColumnInfo(name = "class_type") val classType: String?,
    @ColumnInfo(name = "source_location") val sourceLocation: String?,
    @ColumnInfo(name = "active_region_num") val activeRegionNum: Long?,
    val link: String?
) {
    @Ignore var instruments: List<InstrumentEntity>? = null
    @Ignore var linkedEvents: List<LinkedEventEntity>? = null
}