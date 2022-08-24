package com.mondialrelay.chucknorrisapp.domain.port.spi

import com.mondialrelay.chucknorrisapp.domain.model.UserModel

interface UserSpi {

    suspend fun login(user: String, password: String): UserModel

}