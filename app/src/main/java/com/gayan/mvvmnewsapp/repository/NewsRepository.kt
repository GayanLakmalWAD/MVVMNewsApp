package com.gayan.mvvmnewsapp.repository

import com.gayan.mvvmnewsapp.api.RetrofitInstance
import com.gayan.mvvmnewsapp.db.ArticleDatabase
import com.gayan.mvvmnewsapp.models.Article

/** The Repository acts as the "Single Source of Truth." Its job is to decide whether to
 * fetch data from the network (API) or from the local database (ArticleDatabase)
 * */
class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticles(article: Article) = db.getArticleDao().deleteArticle(article)
}