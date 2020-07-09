package com.toy.toynews.repository

import com.toy.toynews.dto.newsItem
import io.reactivex.Single

interface NewsRepository {
    fun getNews(keyword : String = "",
                category : String = "",
                country : String) : Single<newsItem>
}