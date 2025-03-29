package com.example.myai_assistant.data

import com.example.myai_assistant.data.BookmarkDao
import com.example.myai_assistant.data.BookmarkEntity
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