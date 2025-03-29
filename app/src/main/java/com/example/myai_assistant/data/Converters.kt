package com.example.myai_assistant.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(list: List<String>): String{
        return list.joinToString(separator = "||")
    }

    @TypeConverter
    fun toList(data: String): List<String>{
        return if(data.isNotEmpty()) data.split("||") else emptyList()
    }
}