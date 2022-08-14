package com.mondialrelay.chucknorrisapp.domain.usecase

import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import com.mondialrelay.chucknorrisapp.domain.port.api.JokeApi
import com.mondialrelay.chucknorrisapp.domain.port.spi.JokeSpi

class JokeUseCase(
    private val jokeSpi: JokeSpi
) : JokeApi {

    override suspend fun fetch(): JokeModel {
        return jokeSpi.fetch()
    }

}