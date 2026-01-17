package com.gayan.mvvmnewsapp.repository

import com.gayan.mvvmnewsapp.db.ArticleDatabase

/** The Repository acts as the "Single Source of Truth." Its job is to decide whether to
 * fetch data from the network (API) or from the local database (ArticleDatabase)
 * */
class NewsRepository(
    val db: ArticleDatabase
) {

}