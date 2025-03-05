package com.kabeCoder.pokemonapp.core.database.mappers

import com.kabeCoder.pokemonapp.core.database.entity.PokemonEntity
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        name = name,
        url = url
    )
}

fun Pokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        url = url,
        createdAt = System.currentTimeMillis()
    )
}