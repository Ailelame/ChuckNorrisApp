package com.mondialrelay.chucknorrisapp.domain.port.api

import com.mondialrelay.chucknorrisapp.domain.model.UserModel

interface UserApi {
    suspend fun login(user: String, passsword: String): UserModel
}