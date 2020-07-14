package com.toy.toynews.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import com.toy.toynews.base.BaseViewModel
import com.toy.toynews.dto.Article
import com.toy.toynews.repository.NewsRepository
import com.toy.toynews.utils.SingleLiveEvent
import io.reactivex.functions.Consumer

class MainViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {
    private val _isLoadFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoadFinished: LiveData<Any>
        get() = _isLoadFinished

    var newsList: ArrayList<Article> = ArrayList()

    private fun newsListClear(){
        newsList.clear()
    }

    fun loadNews(keyword : String = "",
                category : String = "",
                country : String){
        apiCall(newsRepository.getNews(keyword, category, country),
        onSuccess = Consumer {
            newsListClear()
            for(i in it.articles) {
                i.urlToImage?.let { newsList.add(i) }
            }
            _isLoadFinished.call()
        },
        indicator = true)
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("viewModel","OnCLEARED")
    }
}