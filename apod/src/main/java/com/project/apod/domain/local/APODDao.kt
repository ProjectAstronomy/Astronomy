package com.project.apod.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.apod.entities.local.APODEntity

@Dao
interface APODDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(apodEntity: APODEntity)

    @Query("SELECT * FROM a_picture_of_the_day_table")
    suspend fun getAll(): List<APODEntity>
}