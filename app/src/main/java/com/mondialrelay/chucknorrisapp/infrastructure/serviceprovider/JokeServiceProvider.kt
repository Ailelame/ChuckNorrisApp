package com.mondialrelay.chucknorrisapp.infrastructure.serviceprovider

import android.util.Log
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
            Log.e("JokeServiceProvider", ex.stackTraceToString())
            JokeModel(text = ex.message ?: "!", rating = 0.0f )
        }
    }

}