package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_detail

import com.kabeCoder.pokemonapp.core.domain.util.DataError

sealed interface PokemonDetailEvent {
    data class Error(val error: DataError.Network): PokemonDetailEvent
}