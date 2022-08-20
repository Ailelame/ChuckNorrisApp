package com.mondialrelay.chucknorrisapp.infrastructure.data.http.iochucknorris

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface IoChuckNorrisEndpoint {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeDto
}

val ioChuckNorrisEndpoint: IoChuckNorrisEndpoint by lazy {
    rf.create(IoChuckNorrisEndpoint::class.java)
}

private val rf by lazy {
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.chucknorris.io/")
        .client(okHttp)
        .build()
}

private val okHttp by lazy {
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor(Interceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (ex: Exception) {
                throw ex
            }
        })
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .build()
}