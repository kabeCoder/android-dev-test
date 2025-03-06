package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list

import androidx.compose.runtime.Immutable
import com.kabeCoder.pokemonapp.pokemon.presentation.models.PokemonUi

@Immutable
data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemon: List<PokemonUi> = emptyList(),
    val next: String? = null,
    val hasMore: Boolean = true
)
