package com.example.MyAIassistant.data.repository

import com.example.MyAIassistant.data.local.BookmarkDao
import com.example.MyAIassistant.data.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao){
    fun getAllBookmarks(): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getAllBookmarks()
    }

    suspend fun insertBookmark(bookmark: BookmarkEntity) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun deleteBookmark(bookmark: BookmarkEntity) {
        bookmarkDao.deleteBookmark(bookmark)
    }

    suspend fun deleteByKeyword(keyword: String) {
        bookmarkDao.deleteByKeyword(keyword)
    }
}