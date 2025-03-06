package com.kabeCoder.pokemonapp.pokemon.data.networking

import com.kabeCoder.pokemonapp.core.data.networking.constructRoute
import com.kabeCoder.pokemonapp.core.data.networking.get
import com.kabeCoder.pokemonapp.core.domain.util.DataError
import com.kabeCoder.pokemonapp.core.domain.util.Result
import com.kabeCoder.pokemonapp.core.domain.util.map
import com.kabeCoder.pokemonapp.pokemon.data.mappers.toPokemon
import com.kabeCoder.pokemonapp.pokemon.data.mappers.toPokemonDetail
import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.PokemonDetailDto
import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.PokemonResponseDto
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDetail
import io.ktor.client.HttpClient

class KtorRemotePokemonDataSource(
    private val httpClient: HttpClient
) : PokemonDataSource {

    private var nextUrl: String? = null

    override suspend fun getPokemon(nextUrl: String?): Result<List<Pokemon>, DataError.Network> {
        val url = nextUrl ?: constructRoute("/pokemon")

        return httpClient.get<PokemonResponseDto>(route = url)
            .map { response ->
                this.nextUrl = response.next
                response.results.map { it.toPokemon() }
            }
    }

    override suspend fun getPokemonDetail(url: String): Result<PokemonDetail, DataError.Network> {
        return httpClient.get<PokemonDetailDto>(route = url)
            .map { it.toPokemonDetail() }
    }

    override suspend fun getNextPageUrl(): Result<String, DataError.Network> {
        return nextUrl?.let {
            Result.Success(it)
        } ?: Result.Error(DataError.Network.NOT_FOUND)
    }
}