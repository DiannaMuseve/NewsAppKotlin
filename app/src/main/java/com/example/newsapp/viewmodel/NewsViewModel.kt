package com.example.newsapp.viewmodel

import NewsResponse
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsResponse = MutableLiveData<Response<NewsResponse>>()
    val newsResponse: LiveData<Response<NewsResponse>> get() = _newsResponse

    // Get cached news from the Room database
    val cachedNews: LiveData<List<Article>> = repository.getCachedNews()

    // Fetch fresh news from the API
    fun fetchNews(query: String, from: String, to: String, sortBy: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getNews(query, from, to, sortBy, apiKey)
                _newsResponse.postValue(response)
            } catch (e: Exception) {
                // Handle error
                Log.e("NewsViewModel","${e.message}")
            }
        }
    }
}