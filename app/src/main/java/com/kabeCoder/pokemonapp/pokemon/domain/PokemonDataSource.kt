package com.kabeCoder.pokemonapp.pokemon.domain

import com.kabeCoder.pokemonapp.core.domain.util.DataError
import com.kabeCoder.pokemonapp.core.domain.util.Result

interface PokemonDataSource {
    suspend fun getPokemon():  Result<List<Pokemon>, DataError.Network>
    suspend fun getPokemonDetail(url: String): Result<PokemonDetail, DataError.Network>
}