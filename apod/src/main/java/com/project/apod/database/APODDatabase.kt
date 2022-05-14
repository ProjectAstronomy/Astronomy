package com.project.apod.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.apod.domain.local.APODDao
import com.project.apod.entities.local.APODEntity

@Database(entities = [APODEntity::class], version = 1)
abstract class APODDatabase : RoomDatabase() {
    abstract fun apodDao(): APODDao
}