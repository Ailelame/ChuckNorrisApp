package com.mondialrelay.chucknorrisapp.application.di

import org.koin.dsl.module

val appModule = module {
    includes(
        uiModule,
        domainModule,
        infrastructureModule
    )
}