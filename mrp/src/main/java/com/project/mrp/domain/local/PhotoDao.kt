package com.project.mrp.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.mrp.entities.local.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo_entity_table WHERE sol = :sol AND rover_name = :roverName")
    fun getPhotosBySolAndRoverName(sol: Long, roverName: String): List<PhotoEntity>

    @Insert
    fun insert(photoEntity: PhotoEntity)
}