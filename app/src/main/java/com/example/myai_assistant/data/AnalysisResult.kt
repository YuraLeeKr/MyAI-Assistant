package com.example.myai_assistant.data

data class AnalysisResult(
    val keyword: String,
    val summary: String,
    val sentiment: String,
    val newsList: List<String>
)
