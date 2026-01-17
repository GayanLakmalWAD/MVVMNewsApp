package com.gayan.mvvmnewsapp.api

import com.gayan.mvvmnewsapp.ui.NewsResponse
import com.gayan.mvvmnewsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("Country")
        countryCode: String = "us",
        @Query("Page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY,

    ) : Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("Page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY,

        ) : Response<NewsResponse>

}