package com.mondialrelay.chucknorrisapp.domain.port.spi

import com.mondialrelay.chucknorrisapp.domain.model.JokeModel

interface JokeSpi {

    suspend fun fetch(): JokeModel

}