package com.gayan.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import com.gayan.mvvmnewsapp.repository.NewsRepository

/** The ViewModel's job is to hold and manage UI-related data in a lifecycle-conscious way.
1. It communicates with the Repository.
2. It survives configuration changes (like rotating the screen).
3. By taking newsRepository in its constructor, it follows Dependency Injection principles.
 */
class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
}