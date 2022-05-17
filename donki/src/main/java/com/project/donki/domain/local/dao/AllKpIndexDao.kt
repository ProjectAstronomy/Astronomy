package com.project.donki.domain.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.donki.database.ALL_KP_INDEX_TABLE
import com.project.donki.database.GEOMAGNETIC_STORM_ID
import com.project.donki.entities.local.*

@Dao
interface AllKpIndexDao {
    @Query("SELECT * FROM $ALL_KP_INDEX_TABLE WHERE $GEOMAGNETIC_STORM_ID = :gstID")
    suspend fun getAllKpIndexByGeomagneticStormId(gstID: String): List<AllKpIndexEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllKpIndex(allKpIndexEntity: AllKpIndexEntity)
}