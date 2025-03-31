package com.example.MyAIassistant.data.model

data class AnalysisResult(
    val keyword: String,
    val summary: String,
    val sentiment: String,
    val newsList: List<String>
)
