package com.project.donki.entities.local

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = INSTRUMENT_TABLE,
    foreignKeys = [ForeignKey(
        entity = SolarFlareEntity::class,
        parentColumns = [SOLAR_FLARE_ID],
        childColumns = [SOLAR_FLARE_ID]
    )],
    indices = [Index(value = ["id", SOLAR_FLARE_ID])]
)
class InstrumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "display_name") val displayName: String?,
    @ColumnInfo(name = SOLAR_FLARE_ID) val flrID: String
) : Parcelable
