package com.mondialrelay.chucknorrisapp

import android.app.Application
import com.mondialrelay.chucknorrisapp.di.appModule
import com.mondialrelay.chucknorrisapp.di.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChuckNorrisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@ChuckNorrisApplication)
            modules(appModule, remoteModule)
        }
    }

}