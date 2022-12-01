package com.example.newsfetcher.di

import android.util.Log
import androidx.room.Room
import com.example.newsfetcher.AppDataBase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://newsapi.org/"
const val API_KEY ="4f26925b85824439a8d15410472beff9"
const val APP_DATABASE = "APP_DATABASE"
val netWorkModule = module{
    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor{ message ->
                    Log.d("OkHttp",message)
                }.apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }

            )
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

}

val databaseModule = module {

    single {
        Room
            .databaseBuilder(androidContext(), AppDataBase::class.java, APP_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<AppDataBase>().bookMarksDao()
    }
}