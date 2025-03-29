package com.example.myai_assistant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myai_assistant.data.BookmarkEntity
import com.example.myai_assistant.data.BookmarkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: BookmarkRepository): ViewModel() {

    //Flow -> StateFlow로 변환해서 UI에서 쉽게 사용할 수 있도록
    val bookmarks: StateFlow<List<BookmarkEntity>> =
        repository.getAllBookmarks()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addBookmark(bookmark: BookmarkEntity){
        viewModelScope.launch { //비동기 처리
            repository.insertBookmark(bookmark)
        }
    }

    fun removeBookmark(bookmark: BookmarkEntity){
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }

    fun removeByKeyword(keyword: String){
        viewModelScope.launch {
            repository.deleteByKeyword(keyword)
        }
    }
}
