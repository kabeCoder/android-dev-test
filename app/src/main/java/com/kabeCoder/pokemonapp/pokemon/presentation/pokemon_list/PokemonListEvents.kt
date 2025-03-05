package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list

import com.kabeCoder.pokemonapp.core.domain.util.DataError

sealed interface PokemonListEvent {
    data class Error(val error: DataError.Network): PokemonListEvent
}