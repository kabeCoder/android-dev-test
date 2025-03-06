package com.kabeCoder.pokemonapp.core.presentation.navigation

import android.util.Base64
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed interface Routes {

    @Serializable
    data object Pokemon : Routes

    @Serializable
    data object PokemonList : Routes

    @Serializable
    data class PokemonDetail(val url: String) : Routes {
        companion object {
            fun createRoute(url: String): String {
                val json = Json.encodeToString(PokemonDetail(url))
                val encodedJson = Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
                return "pokemonDetail/$encodedJson"
            }

            fun fromRoute(encodedJson: String): PokemonDetail {
                val decodedJson = String(Base64.decode(encodedJson, Base64.URL_SAFE))
                return Json.decodeFromString(decodedJson)
            }
        }
    }
}