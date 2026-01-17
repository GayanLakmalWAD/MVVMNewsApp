package com.gayan.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gayan.mvvmnewsapp.R
import com.gayan.mvvmnewsapp.adapters.NewsAdapter
import com.gayan.mvvmnewsapp.databinding.FragmentSavedNewsBinding

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private lateinit var newsAdapter: NewsAdapter

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private val TAG = "SavedNewsFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSavedNewsBinding.bind(view)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}