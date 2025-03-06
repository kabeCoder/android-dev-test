package com.kabeCoder.pokemonapp.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object Pokemon : Routes

    @Serializable
    data object PokemonList : Routes

    @Serializable
    data object PokemonDetail : Routes
}