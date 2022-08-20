package com.mondialrelay.chucknorrisapp.infrastructure.serviceprovider

import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import com.mondialrelay.chucknorrisapp.domain.port.spi.JokeSpi
import com.mondialrelay.chucknorrisapp.infrastructure.data.http.iochucknorris.ioChuckNorrisEndpoint

class JokeServiceProvider : JokeSpi {

    override suspend fun fetch(): JokeModel {
        return try {
            ioChuckNorrisEndpoint
                .getRandomJoke()
                .toModel()
        } catch (ex: Exception) {
            JokeModel(ex.message ?: "!")
        }
    }

}