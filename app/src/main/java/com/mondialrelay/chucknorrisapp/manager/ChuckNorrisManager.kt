package com.mondialrelay.chucknorrisapp.manager

import com.mondialrelay.chucknorrisapp.endpoint.ChuckNorrisEndpoint
import com.mondialrelay.chucknorrisapp.models.Joke

class ChuckNorrisManager(private val endpoint: ChuckNorrisEndpoint) {

    suspend fun getJoke(): Joke {
        return endpoint.getRandomJoke()
    }

}