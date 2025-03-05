package com.kabeCoder.pokemonapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kabeCoder.pokemonapp.core.database.dao.PokemonDao
import com.kabeCoder.pokemonapp.core.database.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1
)

abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}