package com.devv.newsmvvm.models

import com.devv.newsmvvm.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)