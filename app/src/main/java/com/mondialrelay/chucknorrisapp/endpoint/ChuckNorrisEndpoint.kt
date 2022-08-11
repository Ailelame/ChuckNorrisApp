package com.mondialrelay.chucknorrisapp.endpoint

import com.mondialrelay.chucknorrisapp.models.Joke
import retrofit2.http.GET

interface ChuckNorrisEndpoint {

    @GET("jokes/random")
    fun getRandomJoke() : Joke
}