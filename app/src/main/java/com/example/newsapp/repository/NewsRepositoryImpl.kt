package com.example.newsapp.repository

import NewsResponse
import androidx.lifecycle.LiveData
import com.example.newsapp.api.ArticleDao
import com.example.newsapp.api.NewsApiService
import com.example.newsapp.api.NewsDatabase
import com.example.newsapp.models.Article
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
//    private val articleDao: ArticleDao
    private val newsDatabase: NewsDatabase
) : NewsRepository {

    private val articleDao: ArticleDao = newsDatabase.articleDao()

    override suspend fun getNews(query: String, from: String, to: String, sortBy: String, apiKey: String): Response<NewsResponse> {
        val response = apiService.getNews(query, from, to, sortBy, apiKey)

//        if (response.isSuccessful) {
//            response.body()?.let { newsResponse ->
//                // Clear existing articles before inserting new ones
//               // articleDao.deleteAllArticles() // or deleteAllArticlesAndReturnCount() if you need the count
//                // Cache articles in the Room database
//               // articleDao.insertArticles(newsResponse.articles)
//            }
//        }
        return response

    }

    override fun getCachedNews(): LiveData<List<Article>> {
        return articleDao.getAllArticles()
    }
}