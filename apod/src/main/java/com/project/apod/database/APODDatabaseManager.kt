package com.project.apod.database

import android.content.Context
import androidx.room.Room
import com.project.apod.entities.APODEntity

class APODDatabaseManager private constructor(private val database: APODDatabase) {
    suspend fun insert(apodEntity: APODEntity): Unit = database.apodDao().insert(apodEntity)

    suspend fun getAll(): List<APODEntity> = database.apodDao().getAll()

    companion object {
        private const val DATABASE_NAME = "a_picture_of_the_day_table"

        @Volatile
        private var INSTANCE: APODDatabaseManager? = null

        @JvmStatic
        fun getInstance(context: Context): APODDatabaseManager = INSTANCE ?: run {
                val database =
                    Room.databaseBuilder(context, APODDatabase::class.java, DATABASE_NAME).build()
                APODDatabaseManager(database).also { manager -> INSTANCE = manager }
            }
    }
}