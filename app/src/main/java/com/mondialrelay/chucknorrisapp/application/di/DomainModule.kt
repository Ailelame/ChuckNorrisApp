package com.mondialrelay.chucknorrisapp.application.di

import com.mondialrelay.chucknorrisapp.domain.port.api.JokeApi
import com.mondialrelay.chucknorrisapp.domain.port.api.UserApi
import com.mondialrelay.chucknorrisapp.domain.usecase.UseCase
import org.koin.dsl.module

val domainModule = module {

    single { UseCase(userSpi = get(), jokeSpi = get()) }

    single { get<UseCase>() as UserApi }

    single { get<UseCase>() as JokeApi }

}