package com.kabeCoder.pokemonapp.core.database.mappers

import com.kabeCoder.pokemonapp.core.database.entity.PokemonEntity
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDetail
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonImage
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonType
import com.kabeCoder.pokemonapp.pokemon.domain.TypeInfo

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        name = name,
        url = url,
        order = order,
        types = type,
        weight = weight
    )
}

fun Pokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        url = url,
        order = order,
        type = types,
        weight = weight,
        createdAt = System.currentTimeMillis()
    )
}


fun Pokemon.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(
        name = this.name,
        order = this.order,
        sprites = PokemonImage(frontDefault = ""),
        types = this.types.mapIndexed { index, typeName ->
            PokemonType(slot = index + 1, type = TypeInfo(name = typeName, url = ""))
        },
        weight = this.weight
    )
}