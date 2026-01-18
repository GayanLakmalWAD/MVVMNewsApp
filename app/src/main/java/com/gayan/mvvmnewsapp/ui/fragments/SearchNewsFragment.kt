package com.gayan.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gayan.mvvmnewsapp.R
import com.gayan.mvvmnewsapp.adapters.NewsAdapter
import com.gayan.mvvmnewsapp.databinding.FragmentSearchNewsBinding
import com.gayan.mvvmnewsapp.db.ArticleDatabase
import com.gayan.mvvmnewsapp.repository.NewsRepository
import com.gayan.mvvmnewsapp.ui.NewsViewModel
import com.gayan.mvvmnewsapp.ui.NewsViewModelProviderFactory
import com.gayan.mvvmnewsapp.util.Constants.Companion.QUERY_PAGE_SIZE
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
            requireActivity().application,
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

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.articleFragment, bundle)
        }
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
                    hideErrorMessage()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults/QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchNewsPage == totalPages

                        if (isLastPage){
                            binding.rvSearchNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG).show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideErrorMessage() {
        binding.rvSearchNews.visibility = View.VISIBLE
        binding.itemErrorMessage.root.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        binding.rvSearchNews.visibility = View.INVISIBLE
        binding.itemErrorMessage.root.visibility = View.VISIBLE
        binding.itemErrorMessage.tvErrorMessage.text = message
        isError = true
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling

            if(shouldPaginate){
                viewModel.searchNews(binding.etSearch.text.toString())
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }

    private fun hideProgressBar() {
        binding.itemErrorMessage.root.visibility = View.INVISIBLE
        isScrolling = false
    }

    private fun showProgressBar() {
        binding.itemErrorMessage.root.visibility = View.VISIBLE
        isScrolling = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}