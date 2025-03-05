package com.kabeCoder.pokemonapp.di

import androidx.room.Room
import com.kabeCoder.pokemonapp.PokemonApp
import com.kabeCoder.pokemonapp.core.data.networking.HttpClientFactory
import com.kabeCoder.pokemonapp.core.database.PokemonDatabase
import com.kabeCoder.pokemonapp.core.database.RoomLocalDataSource
import com.kabeCoder.pokemonapp.core.domain.pokemon.LocalPokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.data.networking.KtorRemotePokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.PokemonListViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.build() }

    single<CoroutineScope> {
        (androidApplication() as PokemonApp).applicationScope
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            PokemonDatabase::class.java,
            "pokemon.db"
        ).build()
    }

    single { get<PokemonDatabase>().pokemonDao }

    singleOf(::KtorRemotePokemonDataSource).bind<PokemonDataSource>()
    singleOf(::RoomLocalDataSource).bind<LocalPokemonDataSource>()

    viewModelOf(::PokemonListViewModel)
}