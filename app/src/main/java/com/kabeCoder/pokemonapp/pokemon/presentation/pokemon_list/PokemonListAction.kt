package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list

import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon

sealed interface PokemonListAction {
    data class OnPokemonClick(val pokemon: Pokemon): PokemonListAction
}