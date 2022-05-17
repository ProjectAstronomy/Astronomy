package com.project.epic.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.epic.database.EPIC_TABLE_NAME
import com.project.epic.entities.local.EPICEntity

@Dao
interface EPICDao {
    @Query("SELECT * FROM $EPIC_TABLE_NAME")
    suspend fun getAll(): List<EPICEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(epicEntity: EPICEntity)
}