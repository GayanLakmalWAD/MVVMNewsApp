package com.gayan.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gayan.mvvmnewsapp.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    //By returning LiveData,
    //Room will automatically notify your UI whenever the data in the database changes.
    // You don't have to call this function again to refresh the list; it happens reactively.
    @Query("SELECT * FROM articles")
    fun getAllArticles() : LiveData<List<Article>>

    //it is marked as suspend because database deletions are "heavy" operations
    //that shouldn't happen on the Main Thread.
    @Delete
    suspend fun deleteArticle(article: Article)
}