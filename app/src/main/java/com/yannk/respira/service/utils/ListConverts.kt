package com.yannk.respira.service.utils

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> = value.split(",").map { it.trim() }

    @TypeConverter
    fun toString(list: List<String>): String = list.joinToString(",")
}