package com.mondialrelay.chucknorrisapp.application.di

import com.mondialrelay.chucknorrisapp.domain.port.spi.JokeSpi
import com.mondialrelay.chucknorrisapp.infrastructure.serviceprovider.JokeServiceProvider
import org.koin.dsl.module

val infrastructureModule = module {
    single<JokeSpi> {
        JokeServiceProvider()
    }
}