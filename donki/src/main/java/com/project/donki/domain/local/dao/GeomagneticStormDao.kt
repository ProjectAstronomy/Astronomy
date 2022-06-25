package com.project.donki.domain.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.donki.database.GEOMAGNETIC_STORM_TABLE
import com.project.donki.entities.local.GeomagneticStormEntity

@Dao
interface GeomagneticStormDao {
    @Query("SELECT * FROM $GEOMAGNETIC_STORM_TABLE")
    suspend fun getAll(): List<GeomagneticStormEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeomagneticStorm(geomagneticStormEntity: GeomagneticStormEntity)
}