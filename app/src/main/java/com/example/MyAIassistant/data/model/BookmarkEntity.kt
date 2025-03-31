package com.example.MyAIassistant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val keyword: String,
    val summary: String,
    val sentiment: String,
    val newsList: List<String> // type converter 필요
)
