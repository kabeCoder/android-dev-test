package com.kabeCoder.pokemonapp.pokemon.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val name: String,
    val order: Int,
    val sprites: PokemonImageDto,
    val types: List<PokemonTypeDto>,
    val weight: Int
)

@Serializable
data class PokemonImageDto(
    @SerialName("front_default") val frontDefault: String
)

@Serializable
data class PokemonTypeDto(
    val slot: Int,
    val type: TypeInfoDto
)

@Serializable
data class TypeInfoDto(
    val name: String,
    val url: String
)