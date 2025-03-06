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

    private var nextUrl: String? = null
    private var isLoadingMore = false

    private fun loadPokemon() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            pokemonDataSource
                .getPokemon()
                .onSuccess { pokemon ->
                    pokemonDataSource.getNextPageUrl().onSuccess { url ->
                        nextUrl = url

                        localPokemonDataSource.insertPokemon(pokemon)

                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                pokemon = pokemon.map { it.toPokemonUi() },
                                next = nextUrl,
                                hasMore = nextUrl != null
                            )
                        }
                    }
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

    fun loadNextPage() {
        if (isLoadingMore || nextUrl == null) return

        isLoadingMore = true
        viewModelScope.launch {
            nextUrl?.let { url ->
                pokemonDataSource
                    .getPokemon(url)
                    .onSuccess { pokemon ->
                        pokemonDataSource.getNextPageUrl().onSuccess { next ->
                            nextUrl = next

                            _state.update { state ->
                                state.copy(
                                    isLoading = false,
                                    pokemon = state.pokemon + pokemon.map { it.toPokemonUi() }, // Append to existing list
                                    next = nextUrl,
                                    hasMore = nextUrl != null
                                )
                            }
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
                    .also { isLoadingMore = false }
            }
        }
    }
}

