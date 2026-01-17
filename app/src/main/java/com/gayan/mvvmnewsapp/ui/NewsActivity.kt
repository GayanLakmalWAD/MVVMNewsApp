package com.gayan.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gayan.mvvmnewsapp.R
import com.gayan.mvvmnewsapp.databinding.ActivityNewsBinding
import com.gayan.mvvmnewsapp.db.ArticleDatabase
import com.gayan.mvvmnewsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Explicitly tell the window to respect system bars (status/nav bars)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)


        // Improved way to find the NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.newsNavHostFragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        navController?.let {
            binding.bottomNavigationView.setupWithNavController(it)
        }

    }
}