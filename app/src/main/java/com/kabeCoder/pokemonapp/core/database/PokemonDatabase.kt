package com.kabeCoder.pokemonapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kabeCoder.pokemonapp.core.database.dao.PokemonDao
import com.kabeCoder.pokemonapp.core.database.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}