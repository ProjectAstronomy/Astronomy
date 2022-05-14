package com.project.apod.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "a_picture_of_the_day_table")
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