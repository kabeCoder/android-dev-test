package com.kabeCoder.pokemonapp.pokemon.data.mappers

import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.PokemonDto
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon

fun PokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        name= name,
        url = url
    )
}