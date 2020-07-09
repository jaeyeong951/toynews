package com.toy.toynews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    //private val DiModule = listOf(serverModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@Application)
//            modules(DiModule)
//        }
    }
}