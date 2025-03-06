package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabeCoder.pokemonapp.core.domain.pokemon.LocalPokemonDataSource
import com.kabeCoder.pokemonapp.core.domain.util.onError
import com.kabeCoder.pokemonapp.core.domain.util.onSuccess
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.presentation.models.toPokemonUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val pokemonDataSource: PokemonDataSource,
    private val localPokemonDataSource: LocalPokemonDataSource
) : ViewModel() {

    private var _state = MutableStateFlow(PokemonListState())
    val state = _state
        .onStart { loadPokemon() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PokemonListState()
        )

    private val _events = Channel<PokemonListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: PokemonListAction) {
        when (action) {
            is PokemonListAction.OnPokemonClick -> {}
        }
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            pokemonDataSource
                .getPokemon()
                .onSuccess { pokemon ->
                    _state.update { it ->
                        it.copy(
                            isLoading = false,
                            pokemon = pokemon.map { it.toPokemonUi() }
                        )
                    }
                    localPokemonDataSource.insertPokemon(pokemon)
                }
                .onError { error ->
                    val localPokemon = localPokemonDataSource.getPokemon().firstOrNull()

                    if (!localPokemon.isNullOrEmpty()) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                pokemon = localPokemon.map { it.toPokemonUi() }
                            )
                        }
                    } else {
                        _state.update { it.copy(isLoading = false) }
                        _events.send(PokemonListEvent.Error(error))
                    }
                }
        }
    }
}