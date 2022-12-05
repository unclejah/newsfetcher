package com.example.newsfetcher

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsfetcher.di.databaseModule
import com.example.newsfetcher.di.netWorkModule
import com.example.newsfetcher.feature.bookmarks.di.bookmarksModule
import com.example.newsfetcher.feature.di.mainScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application () {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(netWorkModule, mainScreenModule,databaseModule, bookmarksModule)
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}