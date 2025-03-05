package com.kabeCoder.pokemonapp.core.database

import com.kabeCoder.pokemonapp.core.database.dao.PokemonDao
import com.kabeCoder.pokemonapp.core.database.mappers.toPokemon
import com.kabeCoder.pokemonapp.core.database.mappers.toPokemonEntity
import com.kabeCoder.pokemonapp.core.domain.pokemon.LocalPokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource (
    private val pokemonDao: PokemonDao
): LocalPokemonDataSource {
    override suspend fun insertPokemon(pokemon: List<Pokemon>) {
       pokemonDao.insertPokemon(pokemon.map { it.toPokemonEntity() })
    }

    override fun getPokemon(): Flow<List<Pokemon>> {
        return pokemonDao.getPokemon().map { pokemonEntities ->
            pokemonEntities.map { it.toPokemon() }
        }
    }
}