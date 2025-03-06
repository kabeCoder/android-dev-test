package com.kabeCoder.pokemonapp.pokemon.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponseDto(
    val results: List<PokemonDto>,
    val next: String?
)