package com.project.apod.domain

import androidx.room.Insert
import androidx.room.Query
import com.project.apod.entities.APODEntity

interface APODDao {
    @Insert
    suspend fun insert(apodEntity: APODEntity)

    @Query("SELECT * FROM a_picture_of_the_day_table")
    suspend fun getAll(): List<APODEntity>
}