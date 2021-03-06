package com.project.donki.domain.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.donki.entities.local.*
import com.project.donki.database.INSTRUMENT_TABLE
import com.project.donki.database.SOLAR_FLARE_ID

@Dao
interface InstrumentDao {
    @Query("SELECT * FROM $INSTRUMENT_TABLE WHERE $SOLAR_FLARE_ID = :flrID")
    suspend fun getInstrumentsBySolarFlareId(flrID: String): List<InstrumentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstruments(instrumentEntity: InstrumentEntity)
}