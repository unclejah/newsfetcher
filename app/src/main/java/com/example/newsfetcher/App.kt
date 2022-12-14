package com.example.newsfetcher

import android.app.Application
import com.example.newsfetcher.di.appModule
import com.example.newsfetcher.feature.di.mainScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{

            androidLogger()
            androidContext(this@App)
            modules(appModule, mainScreenModule)

        }
    }
}