package com.kabeCoder.pokemonapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val url: String,
    val order: Int,
    val type: List<String>,
    val weight: Int,
    val createdAt: Long
)
