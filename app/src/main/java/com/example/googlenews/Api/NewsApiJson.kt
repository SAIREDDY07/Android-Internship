package com.example.googlenews.Api

data class NewsApiJson(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)