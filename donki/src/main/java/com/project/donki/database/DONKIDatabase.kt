package com.project.donki.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.donki.domain.local.dao.*
import com.project.donki.entities.local.*

@Database(
    entities = [
        AllKpIndexEntity::class,
        GeomagneticStormEntity::class,
        InstrumentEntity::class,
        LinkedEventEntity::class,
        SolarFlareEntity::class
    ],
    version = 1
)
abstract class DONKIDatabase : RoomDatabase() {
    abstract fun allKpIndexDao(): AllKpIndexDao
    abstract fun geomagneticStormDao(): GeomagneticStormDao
    abstract fun instrumentDao(): InstrumentDao
    abstract fun linkedEventDao(): LinkedEventDao
    abstract fun solarFlareDao(): SolarFlareDao
}