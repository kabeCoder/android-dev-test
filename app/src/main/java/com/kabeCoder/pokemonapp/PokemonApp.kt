package com.kabeCoder.pokemonapp

import android.app.Application
import com.kabeCoder.pokemonapp.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokemonApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokemonApp)
            androidLogger()

            modules(appModule)
        }
    }
}