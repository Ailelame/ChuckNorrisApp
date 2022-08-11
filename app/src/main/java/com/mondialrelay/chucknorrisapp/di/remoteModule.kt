package com.mondialrelay.chucknorrisapp.di

import com.mondialrelay.chucknorrisapp.endpoint.ChuckNorrisEndpoint
import com.mondialrelay.chucknorrisapp.manager.ChuckNorrisManager
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
    }

    factory {
        get<Retrofit>().create(ChuckNorrisEndpoint::class.java)
    }


    single {
        ChuckNorrisManager(get())
    }

}