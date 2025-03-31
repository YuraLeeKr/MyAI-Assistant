package com.example.MyAIassistant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.MyAIassistant.data.model.BookmarkEntity


@Database(entities = [BookmarkEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao //DAO에 접근하기 위한 추상 메서드
}