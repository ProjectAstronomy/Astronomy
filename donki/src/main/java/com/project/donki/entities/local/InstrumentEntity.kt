package com.project.donki.entities.local

import androidx.room.*
import com.project.donki.database.INSTRUMENT_TABLE
import com.project.donki.database.SOLAR_FLARE_ID

@Entity(
    tableName = INSTRUMENT_TABLE,
    indices = [Index(value = [SOLAR_FLARE_ID])],
    foreignKeys = [ForeignKey(
        entity = SolarFlareEntity::class,
        parentColumns = [SOLAR_FLARE_ID],
        childColumns = [SOLAR_FLARE_ID]
    )]
)
class InstrumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "display_name") val displayName: String?,
    @ColumnInfo(name = SOLAR_FLARE_ID) val flrID: String
)
