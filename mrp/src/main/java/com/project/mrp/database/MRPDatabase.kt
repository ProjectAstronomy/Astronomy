package com.project.mrp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.mrp.domain.local.dao.PhotoDao
import com.project.mrp.domain.local.dao.PhotoManifestDao
import com.project.mrp.entities.local.PhotoEntity
import com.project.mrp.entities.local.PhotoManifestEntity
import com.project.mrp.entities.local.PhotosInformationEntity

@Database(
    entities = [PhotoEntity::class, PhotoManifestEntity::class, PhotosInformationEntity::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class MRPDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun photoManifestDao(): PhotoManifestDao

    companion object {
        const val MRP_DATABASE = "mrp_database"
    }
}