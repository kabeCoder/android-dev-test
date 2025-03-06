package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_detail

import androidx.compose.runtime.Immutable
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDetail

@Immutable
data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemonDetail: PokemonDetail? = null
)