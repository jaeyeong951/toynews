package com.toy.toynews.dto

data class newsItem(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)