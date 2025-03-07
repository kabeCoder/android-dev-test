package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabeCoder.pokemonapp.core.database.mappers.toPokemonDetail
import com.kabeCoder.pokemonapp.core.domain.pokemon.LocalPokemonDataSource
import com.kabeCoder.pokemonapp.core.domain.util.onError
import com.kabeCoder.pokemonapp.core.domain.util.onSuccess
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDataSource
import com.kabeCoder.pokemonapp.pokemon.domain.toPokemon
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemonDataSource: PokemonDataSource,
    private val localPokemonDataSource: LocalPokemonDataSource
) : ViewModel() {

    private var _state = MutableStateFlow(PokemonDetailState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PokemonDetailState()
        )

    private val _events = Channel<PokemonDetailEvent>()
    val events = _events.receiveAsFlow()

    fun loadPokemonDetail(url: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            pokemonDataSource
                .getPokemonDetail(url)
                .onSuccess { pokemonDetail ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            pokemonDetail = pokemonDetail
                        )
                    }
                    localPokemonDataSource.updatePokemon(pokemonDetail.toPokemon())
                }

                .onError { error ->

                    val localPokemon = localPokemonDataSource.getPokemonByUrl(url)

                    if (localPokemon != null) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                pokemonDetail = localPokemon.toPokemonDetail()
                            )
                        }
                    } else {
                        _state.update { it.copy(isLoading = false) }
                        _events.send(PokemonDetailEvent.Error(error))
                    }

                }
        }
    }
}