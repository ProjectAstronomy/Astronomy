package com.project.donki.entities.local

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = SOLAR_FLARE_TABLE, indices = [Index(value = [SOLAR_FLARE_ID], unique = true)])
data class SolarFlareEntity(
    @PrimaryKey @ColumnInfo(name = SOLAR_FLARE_ID) val flrID: String,
    @Ignore var instruments: List<InstrumentEntity>? = null,
    @ColumnInfo(name = "begin_time") val beginTime: String?,
    @ColumnInfo(name = "peak_time") val peakTime: String?,
    @ColumnInfo(name = "end_time") val endTime: String?,
    @ColumnInfo(name = "class_type") val classType: String?,
    @ColumnInfo(name = "source_location") val sourceLocation: String?,
    @ColumnInfo(name = "active_region_num") val activeRegionNum: Long?,
    @Ignore var linkedEvents: List<LinkedEventEntity>? = null,
    val link: String?
) : Parcelable