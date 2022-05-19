package com.project.epic.entities.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.epic.database.EPIC_TABLE_NAME
import com.project.epic.database.EPIC_TABLE_PRIMARY_KEY
import com.project.epic.entities.remote.AttitudeQuaternions
import com.project.epic.entities.remote.CentroidCoordinates
import com.project.epic.entities.remote.J2000Position

@Entity(tableName = EPIC_TABLE_NAME, indices = [Index(value = [EPIC_TABLE_PRIMARY_KEY], unique = true)])
data class EPICEntity(
    @PrimaryKey val identifier: String,
    val caption: String?,
    val image: String?,
    val version: String?,
    val date: String?,
    @Embedded val attitudeQuaternions: AttitudeQuaternions?,
    @Embedded val centroidCoordinates: CentroidCoordinates?,
    @Embedded(prefix = "dscovr_") val dscovrJ2000Position: J2000Position?,
    @Embedded(prefix = "lunar_") val lunarJ2000Position: J2000Position?,
    @Embedded(prefix = "sun_") val sunJ2000Position: J2000Position?,
)