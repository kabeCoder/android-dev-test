package com.kabeCoder.pokemonapp.core.database

import com.kabeCoder.pokemonapp.core.database.dao.PokemonDao
import com.kabeCoder.pokemonapp.core.database.mappers.toPokemon
import com.kabeCoder.pokemonapp.core.database.mappers.toPokemonEntity
import com.kabeCoder.pokemonapp.core.domain.pokemon.LocalPokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class RoomLocalDataSource (
    private val pokemonDao: PokemonDao
): LocalPokemonDataSource {
    override suspend fun insertPokemon(pokemon: List<Pokemon>) {

        val existingUrls = pokemonDao.getPokemon().first().map { it.url }

        val newPokemon = pokemon.filter { it.url !in existingUrls }

        if (newPokemon.isNotEmpty()) {
            pokemonDao.insertPokemon(newPokemon.map { it.toPokemonEntity() })
        }
    }

    override fun getPokemon(): Flow<List<Pokemon>> {
        return pokemonDao.getPokemon().map { pokemonEntities ->
            pokemonEntities.map { it.toPokemon() }
        }
    }

    override suspend fun getPokemonByUrl(url: String): Pokemon? {
        return pokemonDao.getPokemonByUrl(url)?.toPokemon()
    }

    override suspend fun updatePokemon(pokemon: Pokemon) {
        val existingPokemon = pokemonDao.getPokemonByUrl(pokemon.url)
        if (existingPokemon != null) {
            val updatedPokemonEntity = existingPokemon.copy(
                order = pokemon.order,
                type = pokemon.types,
                weight = pokemon.weight,
                createdAt = System.currentTimeMillis()
            )
            pokemonDao.updatePokemon(updatedPokemonEntity)
        }
    }
}