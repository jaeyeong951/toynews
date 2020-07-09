package com.toy.toynews.repository

import com.toy.toynews.api.NewsService
import com.toy.toynews.dto.newsItem
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsService: NewsService): NewsRepository{
    override fun getNews(keyword: String, category: String, country: String): Single<newsItem> {
        return newsService.getNews(keyword, category, country)
    }
}