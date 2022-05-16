package com.project.donki.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.donki.entities.local.*
import com.project.donki.entities.local.SOLAR_FLARE_TABLE

@Dao
interface SolarFlareDao {
    @Query("SELECT * FROM $SOLAR_FLARE_TABLE")
    suspend fun getAll(): List<SolarFlareEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSolarFlare(solarFlareEntity: SolarFlareEntity)
}