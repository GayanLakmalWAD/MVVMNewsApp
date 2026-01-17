package com.gayan.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gayan.mvvmnewsapp.R
import com.gayan.mvvmnewsapp.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Explicitly tell the window to respect system bars (status/nav bars)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Improved way to find the NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.newsNavHostFragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        navController?.let {
            binding.bottomNavigationView.setupWithNavController(it)
        }
    }
}