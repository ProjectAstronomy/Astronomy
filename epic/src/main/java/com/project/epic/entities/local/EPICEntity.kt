package com.project.epic.entities.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.project.epic.entities.local.EPICEntity.Companion.EPIC_TABLE
import com.project.epic.entities.remote.AttitudeQuaternions
import com.project.epic.entities.remote.CentroidCoordinates
import com.project.epic.entities.remote.J2000Position

//TODO: rename prefix of dscovrJ2000Position, lunarJ2000Position, sunJ2000Position
@Entity(tableName = EPIC_TABLE, indices = [Index(value = ["identifier"], unique = true)])
data class EPICEntity(
    val identifier: String,
    val caption: String?,
    val image: String?,
    val version: String?,
    val date: String?,
    @Embedded val attitudeQuaternions: AttitudeQuaternions?,
    @Embedded val centroidCoordinates: CentroidCoordinates?,
    @Embedded(prefix = "dscovr_") val dscovrJ2000Position: J2000Position?,
    @Embedded(prefix = "lunar_") val lunarJ2000Position: J2000Position?,
    @Embedded(prefix = "sun_") val sunJ2000Position: J2000Position?,
) {
    companion object {
        const val EPIC_TABLE = "epic_table"
    }
}