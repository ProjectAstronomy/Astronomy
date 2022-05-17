package com.project.apod.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.apod.database.TABLE_MEDIA_TYPE_COLUMN
import com.project.apod.database.TABLE_NAME
import com.project.apod.database.TABLE_PRIMARY_KEY
import com.project.apod.database.TABLE_SERVICE_LOCATION_COLUMN

@Entity(tableName = TABLE_NAME, indices = [Index(value = [TABLE_PRIMARY_KEY])])
data class APODEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    @ColumnInfo(name = TABLE_MEDIA_TYPE_COLUMN) val mediaType: String?,
    @ColumnInfo(name = TABLE_SERVICE_LOCATION_COLUMN) val serviceVersion: String?,
    val title: String?,
    val url: String?
)