package com.example.MyAIassistant.data.remote

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.GET

data class AnalyzeRequest(val keyword: String)
data class AnalyzeResponse(
    val keyword: String,
    val summary: String,
    val sentiment: String,
    val newsList: List<String>
)
data class TrendingResponse(val keywords: List<String>)

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/analyze")
    suspend fun analyzeKeyword(@Body request: AnalyzeRequest): AnalyzeResponse

    @GET("/trending")
    suspend fun getTrendingKeywords(): TrendingResponse
}