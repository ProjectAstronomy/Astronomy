package com.project.mrp.entities.local

import androidx.room.*
import com.project.mrp.entities.local.PhotoManifestEntity.Companion.PHOTO_MANIFEST_TABLE

@Entity(tableName = PHOTO_MANIFEST_TABLE, indices = [Index(value = ["name"], unique = true)])
data class PhotoManifestEntity(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "landing_date") val landingDate: String?,
    @ColumnInfo(name = "launch_date") val launchDate: String?,
    val status: String?,
    @ColumnInfo(name = "max_sol") val maxSol: Long?,
    @ColumnInfo(name = "max_date") val maxDate: String?,
    @ColumnInfo(name = "total_photos") val totalPhotos: Long?
) {
    companion object {
        const val PHOTO_MANIFEST_TABLE = "photo_manifest_table"
    }
}