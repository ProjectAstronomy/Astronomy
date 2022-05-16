package com.project.epic.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.epic.domain.local.EPICDao
import com.project.epic.entities.local.EPICEntity

@Database(entities = [EPICEntity::class], version = 1)
abstract class EPICDatabase : RoomDatabase() {
    abstract fun epicDao(): EPICDao

    companion object {
        const val EPIC_DATABASE = "epic_database"
    }
}