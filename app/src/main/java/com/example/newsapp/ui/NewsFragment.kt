package com.example.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe cached news from Room database
        newsViewModel.cachedNews.observe(viewLifecycleOwner) { articles ->
            newsAdapter.submitList(articles)
        }

        // Fetch fresh news from the API
        newsViewModel.fetchNews("apple", "2024-08-27", "2024-08-27", "popularity", "384c812648314266a3cb7e5e6bbd632e")

        lifecycleScope.launch {
            newsViewModel.newsResponse.observe(viewLifecycleOwner) {
                newsAdapter.submitList(it.body()!!.articles)
                binding.recyclerView.adapter = newsAdapter
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter { article ->
            // Handle item click, e.g., navigate to details fragment
            val action = NewsFragmentDirections.actionNewsFragmentToArticleDetailsFragment(article)
            findNavController().navigate(action)
        }
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
