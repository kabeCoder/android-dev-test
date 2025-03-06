package com.kabeCoder.pokemonapp.pokemon.data.mappers

import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.PokemonDetailDto
import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.PokemonImageDto
import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.PokemonTypeDto
import com.kabeCoder.pokemonapp.pokemon.data.networking.dto.TypeInfoDto
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDetail
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonImage
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonType
import com.kabeCoder.pokemonapp.pokemon.domain.TypeInfo

fun PokemonDetailDto.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(
        name = name,
        order = order,
        sprites = sprites.toPokemonImage(),
        types = types.map { it.toPokemonType() },
        weight = weight
    )
}

fun PokemonImageDto.toPokemonImage(): PokemonImage {
    return PokemonImage(
        frontDefault = frontDefault
    )
}

fun PokemonTypeDto.toPokemonType(): PokemonType {
    return PokemonType(
        slot = slot,
        type = type.toTypeInfo()
    )
}

fun TypeInfoDto.toTypeInfo(): TypeInfo {
    return TypeInfo(
        name = name,
        url = url
    )
}

