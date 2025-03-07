package com.kabeCoder.pokemonapp.pokemon.presentation.models

import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon

data class PokemonUi(
    val name: String,
    val url: String
) {
    private val id: String
        get() = url.trimEnd('/').substringAfterLast('/')

    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}

fun Pokemon.toPokemonUi(): PokemonUi {
    return PokemonUi(
        name = name,
        url = url
    )
}
