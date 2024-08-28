package com.example.newsapp.repository

import NewsResponse
import com.example.newsapp.api.NewsApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService
) {
    suspend fun getEverything(
        query: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): NewsResponse {
        return apiService.getEverything(query, from, to, sortBy, apiKey)
    }
}
