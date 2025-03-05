package com.kabeCoder.pokemonapp.pokemon.data.networking

import com.kabeCoder.pokemonapp.core.data.networking.constructRoute
import com.kabeCoder.pokemonapp.core.data.networking.get
import com.kabeCoder.pokemonapp.core.domain.util.DataError
import com.kabeCoder.pokemonapp.core.domain.util.Result
import com.kabeCoder.pokemonapp.core.domain.util.map
import com.kabeCoder.pokemonapp.pokemon.data.mappers.toPokemon
import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.PokemonResponseDto
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDataSource
import io.ktor.client.HttpClient

class KtorRemotePokemonDataSource(
    private val httpClient: HttpClient,
) : PokemonDataSource  {

    override suspend fun getPokemon(): Result<List<Pokemon>, DataError.Network> {
        return httpClient.get<PokemonResponseDto>(
            route = constructRoute("/pokemon")
        ).map { response ->
            response.results.map { it.toPokemon() }
        }
    }
}