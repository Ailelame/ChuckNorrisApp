package com.mondialrelay.chucknorrisapp.application.di

import com.mondialrelay.chucknorrisapp.domain.port.spi.JokeSpi
import com.mondialrelay.chucknorrisapp.domain.port.spi.UserSpi
import com.mondialrelay.chucknorrisapp.infrastructure.serviceprovider.JokeServiceProvider
import com.mondialrelay.chucknorrisapp.infrastructure.serviceprovider.UserServiceProvider
import org.koin.dsl.module

val infrastructureModule = module {

    single { UserServiceProvider() as UserSpi }

    single { JokeServiceProvider() as JokeSpi }

}