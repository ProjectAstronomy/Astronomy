package com.project.mrp.entities.local

import androidx.room.*
import com.project.mrp.entities.local.PhotoEntity.Companion.PHOTO_ENTITY_TABLE
import com.project.mrp.entities.remote.Camera
import com.project.mrp.entities.remote.Rover

@Entity(tableName = PHOTO_ENTITY_TABLE, indices = [Index(value = ["id"], unique = true)])
data class PhotoEntity(
    @PrimaryKey val id: Long,
    val sol: Long?,
    @Embedded(prefix ="camera_") val camera: Camera,
    @ColumnInfo(name = "img_src") val imgSrc: String?,
    @ColumnInfo(name = "earth_date") val earthDate: String?,
    @Embedded(prefix = "rover_") val rover: Rover?
) {
    companion object {
        const val PHOTO_ENTITY_TABLE = "photo_entity_table"
    }
}