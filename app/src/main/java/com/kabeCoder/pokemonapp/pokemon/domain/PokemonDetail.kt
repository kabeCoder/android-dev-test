package com.kabeCoder.pokemonapp.pokemon.domain

import com.kabeCoder.pokemonapp.BuildConfig

data class PokemonDetail(
    val name: String,
    val order: Int,
    val sprites: PokemonImage,
    val types: List<PokemonType>,
    val weight: Int
)

data class PokemonImage(
    val frontDefault: String
)

data class PokemonType(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String,
    val url: String
)

fun PokemonDetail.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name,
        url = "${BuildConfig.POKEMON_API_BASE_URL}pokemon/${this.order}/",
        order = this.order,
        types = this.types.map { it.type.name },
        weight = this.weight,
        createdAt = System.currentTimeMillis()
    )
}