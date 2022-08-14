package com.mondialrelay.chucknorrisapp.application.di

import com.mondialrelay.chucknorrisapp.domain.port.api.JokeApi
import com.mondialrelay.chucknorrisapp.domain.port.spi.JokeSpi
import com.mondialrelay.chucknorrisapp.domain.usecase.JokeUseCase
import org.koin.dsl.module

val domainModule = module {
    single<JokeApi> {
        JokeUseCase(get())
    }
}