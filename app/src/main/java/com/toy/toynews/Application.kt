package com.toy.toynews

import android.app.Application

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