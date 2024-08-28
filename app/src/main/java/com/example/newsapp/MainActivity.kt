package com.example.newsapp

import com.example.newsapp.viewmodel.NewsViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.newsapp.databinding.ActivityMainBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArticleAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.getEverything(
            query = "apple",
            from = "2024-08-27",
            to = "2024-08-27",
            sortBy = "popularity",
            apiKey = "384c812648314266a3cb7e5e6bbd632e"
        )

        viewModel.articles.observe(this, Observer { articles ->
            adapter.updateArticles(articles)
        })
    }
}

