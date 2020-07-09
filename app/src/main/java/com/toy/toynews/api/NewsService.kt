package com.toy.toynews.api

import com.toy.toynews.dto.newsItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/top-headlines")
    fun getNews(
        @Query("q") keyword : String,
        @Query("category") category : String,
        @Query("country") country : String,
        @Query("apiKey") apiKey : String = "794cf994e8b446cd809f23940abf34c9"
    ) : Single<newsItem>
}