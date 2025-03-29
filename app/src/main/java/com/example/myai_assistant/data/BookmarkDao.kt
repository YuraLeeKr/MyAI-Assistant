package com.example.myai_assistant.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmarks ORDER BY id DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE keyword = :keyword")
    suspend fun deleteByKeyword(keyword: String)

}