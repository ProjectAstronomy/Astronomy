package com.project.donki.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.donki.entities.local.LINKED_EVENT_TABLE
import com.project.donki.entities.local.LinkedEventEntity
import com.project.donki.entities.local.SOLAR_FLARE_ID

@Dao
interface LinkedEventDao {
    @Query("SELECT * FROM $LINKED_EVENT_TABLE WHERE $SOLAR_FLARE_ID = :flrID")
    suspend fun getLinkedEventBySolarFlareId(flrID: String): List<LinkedEventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLinkedEvents(linkedEventEntity: LinkedEventEntity)
}