package com.kabeCoder.pokemonapp.pokemon.domain

data class Pokemon(
    val name: String,
    val url: String,
    val order: Int = 0,
    val types: List<String> = emptyList(),
    val weight: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)