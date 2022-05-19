package com.project.apod.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.apod.database.TABLE_MEDIA_TYPE_COLUMN
import com.project.apod.database.TABLE_NAME
import com.project.apod.database.TABLE_PRIMARY_KEY
import com.project.apod.database.TABLE_SERVICE_VERSION_COLUMN

@Entity(tableName = TABLE_NAME, indices = [Index(value = [TABLE_PRIMARY_KEY], unique = true)])
data class APODEntity(
    @PrimaryKey val date: String,
    val copyright: String?,
    val explanation: String?,
    val hdurl: String?,
    @ColumnInfo(name = TABLE_MEDIA_TYPE_COLUMN) val mediaType: String?,
    @ColumnInfo(name = TABLE_SERVICE_VERSION_COLUMN) val serviceVersion: String?,
    val title: String?,
    val url: String?
)