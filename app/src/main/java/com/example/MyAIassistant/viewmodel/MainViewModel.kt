package com.example.MyAIassistant.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MyAIassistant.data.remote.AnalyzeRequest
import com.example.MyAIassistant.data.remote.AnalyzeResponse
import com.example.MyAIassistant.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _result = MutableLiveData<AnalyzeResponse?>()
    val result: LiveData<AnalyzeResponse?> = _result

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _trending = MutableLiveData<List<String>>()
    val  trending: LiveData<List<String>> = _trending

    fun analyzeKeyword(keyword: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = RetrofitInstance.api.analyzeKeyword(AnalyzeRequest(keyword))
                val enrichedResult = response.copy(keyword = keyword)

                _result.value = enrichedResult
                _error.value = null
            } catch (e: Exception) {
                _error.value = "네트워크 오류: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchTrendingKeywords(){
        viewModelScope.launch {
            try{
                val response = RetrofitInstance.api.getTrendingKeywords()
                _trending.value = response.keywords
            } catch (e: Exception){
                Log.e("TrendingError", "실패: ${e.message}")
            }
        }
    }
}
