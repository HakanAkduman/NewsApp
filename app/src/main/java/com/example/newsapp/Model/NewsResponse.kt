package com.example.newsapp.Model

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
