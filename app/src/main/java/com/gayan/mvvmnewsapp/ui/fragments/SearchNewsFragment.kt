package com.gayan.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gayan.mvvmnewsapp.R
import com.gayan.mvvmnewsapp.adapters.NewsAdapter
import com.gayan.mvvmnewsapp.databinding.FragmentSearchNewsBinding
import com.gayan.mvvmnewsapp.db.ArticleDatabase
import com.gayan.mvvmnewsapp.repository.NewsRepository
import com.gayan.mvvmnewsapp.ui.NewsViewModel
import com.gayan.mvvmnewsapp.ui.NewsViewModelProviderFactory
import com.gayan.mvvmnewsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.gayan.mvvmnewsapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private val viewModel: NewsViewModel by activityViewModels {
        NewsViewModelProviderFactory(
            NewsRepository(
                ArticleDatabase(requireContext())
            )
        )
    }

    private lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    private val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchNewsBinding.bind(view)
        setupRecyclerView()

        var job: Job? = null
        binding.etSearch.addTextChangedListener{editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }
        observeSearchNews()
    }

    private fun observeSearchNews() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Log.e(TAG, "Error: ${response.message}")
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}