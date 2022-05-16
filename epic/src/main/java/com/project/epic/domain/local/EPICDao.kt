package com.project.epic.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.epic.entities.local.EPICEntity

@Dao
interface EPICDao {
    @Query("SELECT * FROM epic_table")
    suspend fun getAll(): List<EPICEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(epicEntity: EPICEntity)
}