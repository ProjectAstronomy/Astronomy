package com.project.mrp.domain.local.dao

import androidx.room.*
import com.project.mrp.entities.local.ManifestWithPhotos
import com.project.mrp.entities.local.PhotoManifestEntity
import com.project.mrp.entities.local.PhotosInformationEntity

@Dao
interface PhotoManifestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photoManifestEntity: PhotoManifestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photosInformationEntity: PhotosInformationEntity)

    @Transaction
    @Query("SELECT * FROM photo_manifest_table")
    suspend fun getPhotoManifest(): ManifestWithPhotos
}