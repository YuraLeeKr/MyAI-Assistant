package com.example.myai_assistant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.myai_assistant.data.BookmarkRepository

class BookmarkViewModelFactory (
    private val repository: BookmarkRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}