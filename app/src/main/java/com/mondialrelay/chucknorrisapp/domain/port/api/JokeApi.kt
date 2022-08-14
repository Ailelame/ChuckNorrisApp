package com.mondialrelay.chucknorrisapp.domain.port.api

import com.mondialrelay.chucknorrisapp.domain.model.JokeModel

interface JokeApi {
    suspend fun fetch(): JokeModel
}