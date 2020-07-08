package com.toy.toynews.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.toy.toynews.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseViewModel : ViewModel() {
    private val _startLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingIndicatorEvent: LiveData<Any>
        get() = _startLoadingIndicatorEvent
    private val _stopLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingIndicatorEvent: LiveData<Any>
        get() = _stopLoadingIndicatorEvent

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun <T> apiCall(single: Single<T>, onSuccess: Consumer<in T>,
                    onError: Consumer<in Throwable> = Consumer {
                        it.message?.let {msg ->
                            Log.e("오류 발생", msg)
                        }
                    },
                    indicator : Boolean = false, timeout: Long = 5){
        addDisposable(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(timeout, TimeUnit.SECONDS)
            .doOnSubscribe{ if(indicator) startLoadingIndicator() }
            .doAfterTerminate { stopLoadingIndicator() }
            .subscribe(onSuccess, onError))
    }

    fun startLoadingIndicator(){
        _startLoadingIndicatorEvent.call()
    }

    fun stopLoadingIndicator(){
        _stopLoadingIndicatorEvent.call()
    }
}