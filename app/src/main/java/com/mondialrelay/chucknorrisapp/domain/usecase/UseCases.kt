package com.mondialrelay.chucknorrisapp.domain.usecase

import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import com.mondialrelay.chucknorrisapp.domain.model.UserModel
import com.mondialrelay.chucknorrisapp.domain.port.api.JokeApi
import com.mondialrelay.chucknorrisapp.domain.port.api.UserApi
import com.mondialrelay.chucknorrisapp.domain.port.spi.JokeSpi
import com.mondialrelay.chucknorrisapp.domain.port.spi.UserSpi

class UseCase(
    private val userSpi: UserSpi,
    private val jokeSpi: JokeSpi,
) : UserApi, JokeApi {

    override suspend fun login(user: String, passsword: String): UserModel {
        return userSpi.login(user, passsword)
    }

    override suspend fun fetch(): JokeModel {
        return jokeSpi.fetch()
    }

}