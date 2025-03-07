package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list

import com.kabeCoder.pokemonapp.pokemon.presentation.models.PokemonUi

sealed interface PokemonListAction {
    data class OnPokemonClick(val url: String): PokemonListAction
}