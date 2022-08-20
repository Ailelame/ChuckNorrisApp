package com.mondialrelay.chucknorrisapp.application

import android.content.res.Configuration
import android.os.StrictMode
import android.util.Log
import com.mondialrelay.chucknorrisapp.BuildConfig
import com.mondialrelay.chucknorrisapp.application.di.appModule
import com.mondialrelay.chucknorrisapp.application.di.domainModule
import com.mondialrelay.chucknorrisapp.application.di.infrastructureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChuckNorrisApplication : android.app.Application() {

    // region -------- OVERRIDE

    override fun onCreate() {
        Thread.setDefaultUncaughtExceptionHandler { _: Thread, e: Throwable ->
            Log.wtf("!!!", e)
            throw e
        }
        if (BuildConfig.DEBUG)
            initializeDebug()
        super.onCreate()
        Log.d("App", "App.onCreate")
        initializeDI()
        initialize()
    }

    /*
     Called by the system when the device configuration changes while your component is running.
     Overriding this method is totally optional!
    */
    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d("App", "App.onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }

    /*
     This is called when the overall system is running low on memory,
     and would like actively running processes to tighten their belts.
     Overriding this method is totally optional!
     */
    override fun onLowMemory() {
        Log.d("App", "App.onLowMemory")
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        Log.d("App", "App.onTrimMemory")
        super.onTrimMemory(level)
    }

    // endregion

    // region -------- APP INITIALIZING

    private fun initialize() {
    }

    private fun initializeDI() {
        startKoin {
            androidLogger()
            androidContext(this@ChuckNorrisApplication)
            modules(
                appModule,
                domainModule,
                infrastructureModule,
            )
//            modules(
//                with(Module(this@App)) {
//                    if (BuildConfig.DEBUG) debugModule else appModule
//                }
//            )
        }
    }

    private fun initializeDebug() {
//        Timber.plant(Timber.DebugTree())
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

    // endregion

}

/* TODO Q/R pour Thomas
 * +- ViewModel
 *      +- utilisation de ViewModelProvider ? by viewModels() ? passage parametres constructeur ?
 *      +- comment est gere lifecycle si creation via Koin ?
 * +- Binding
 *      +- two-way (ex: click sur bouton) ?
 *      +-  V <------ VM ------> M
 *          V . . . > VM
 * +- Activity
 *      +- Navgraph
 *      +- Structure des layouts / fragments
 *      +- Jetpack Compose
 * +- Exemple service (scanner ?)
 * +- Divers
 *      +- fichier de config (assets?)
 *      +- adherence au materiel (differents constructeurs)
 * +- Gestion des erreurs
 *      +- Gestion erreurs dans Retrofit
 *      +- Exceptions, validable ?
 * +- RetroFit
 *      +- Gestion erreurs dans Retrofit
 *      +- suspend + try/catch ?
 * +- Custom Metrics ?
 */

/* TODO A discuter avec Thomas
 * +- DI, quoi ?
 * +- Structure du projet
 *      +- Organisation des repertoires
 *      +- modules
 *      +- clean archi
 */