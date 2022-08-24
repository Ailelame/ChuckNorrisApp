package com.mondialrelay.chucknorrisapp.infrastructure.serviceprovider

import com.mondialrelay.chucknorrisapp.domain.model.UserModel
import com.mondialrelay.chucknorrisapp.domain.port.spi.UserSpi

class UserServiceProvider : UserSpi {

    override suspend fun login(user: String, password: String): UserModel {
        return UserModel(
            user = user,
            token = if (user == "chuck") "jwt-token" else null
        )
    }

}