package com.project.apod.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.apod.di.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME, indices = [Index(value = ["date"], unique = true)])
data class APODEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val copyright: String?,
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    @ColumnInfo(name = "media_type") val mediaType: String?,
    @ColumnInfo(name = "service_version") val serviceVersion: String?,
    val title: String?,
    val url: String?
) : Parcelable