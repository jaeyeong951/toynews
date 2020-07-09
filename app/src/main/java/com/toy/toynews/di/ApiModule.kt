package com.toy.toynews.di

import com.toy.toynews.api.NewsService
import com.toy.toynews.repository.NewsRepository
import com.toy.toynews.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object ApiModule {

    @Provides
    fun provideNewsRepository(newsService: NewsService) : NewsRepository{
        return NewsRepositoryImpl(newsService)
    }

    @Provides
    fun provideNewsService(retrofit: Retrofit):NewsService = retrofit.create(NewsService::class.java)

    @Provides
    fun provideNewsApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://newsapi.org").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()).build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient().newBuilder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(logging)

        return httpClientBuilder.build()
    }
}
