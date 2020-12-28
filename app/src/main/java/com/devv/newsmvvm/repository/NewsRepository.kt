package com.devv.newsmvvm.repository

import com.devv.newsmvvm.api.RetrofitInstance
import com.devv.newsmvvm.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber:Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber:Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)
}