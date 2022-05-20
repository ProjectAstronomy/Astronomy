package com.project.mrp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun convertToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun convertToList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}