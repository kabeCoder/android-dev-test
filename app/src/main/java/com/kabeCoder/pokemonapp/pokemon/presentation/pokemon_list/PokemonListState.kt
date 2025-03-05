package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list

import androidx.compose.runtime.Immutable
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon

@Immutable
data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemon: List<Pokemon> = emptyList()
)
