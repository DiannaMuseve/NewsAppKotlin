package com.example.newsapp.repository

import NewsResponse
import androidx.lifecycle.LiveData
import com.example.newsapp.api.ArticleDao
import com.example.newsapp.api.NewsApiService
import com.example.newsapp.models.Article
import retrofit2.Response
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNews(query: String, from: String, to: String, sortBy: String, apiKey: String): Response<NewsResponse>
    fun getCachedNews(): LiveData<List<Article>>
}
