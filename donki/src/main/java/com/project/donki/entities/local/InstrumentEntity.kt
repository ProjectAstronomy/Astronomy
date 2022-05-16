package com.project.donki.entities.local

import androidx.room.*

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
