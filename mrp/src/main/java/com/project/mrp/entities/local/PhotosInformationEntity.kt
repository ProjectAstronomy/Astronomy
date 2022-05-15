package com.project.mrp.entities.local

import androidx.room.*
import com.project.mrp.entities.local.PhotosInformationEntity.Companion.PHOTOS_INFORMATION_TABLE

@Entity(
    tableName = PHOTOS_INFORMATION_TABLE,
    indices = [Index(value = ["id", "photo_manifest_name"], unique = true)]
)
data class PhotosInformationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "earth_date") val earthDate: String?,
    @ColumnInfo(name = "total_photos") val totalPhotos: Long?,
    val cameras: List<String>?,
    @ColumnInfo(name = "photo_manifest_name") val name: String
) {
    companion object {
        const val PHOTOS_INFORMATION_TABLE = "photos_information_table"
    }
}
