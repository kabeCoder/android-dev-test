package com.kabeCoder.pokemonapp.core.domain.pokemon

import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import kotlinx.coroutines.flow.Flow

interface LocalPokemonDataSource {
    suspend fun insertPokemon(pokemon: List<Pokemon>)
    fun getPokemon(): Flow<List<Pokemon>>
}