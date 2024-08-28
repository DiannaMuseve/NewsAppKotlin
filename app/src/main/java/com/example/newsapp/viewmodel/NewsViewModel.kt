package com.example.newsapp.viewmodel

import Article
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val articles = MutableLiveData<List<Article>>()

    fun getEverything(query: String, from: String, to: String, sortBy: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getEverything(query, from, to, sortBy, apiKey)
                articles.postValue(response.articles)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}
