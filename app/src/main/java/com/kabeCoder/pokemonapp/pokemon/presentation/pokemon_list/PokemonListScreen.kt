package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kabeCoder.pokemonapp.core.presentation.util.ObserveAsEvents
import com.kabeCoder.pokemonapp.core.presentation.util.toString
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components.PokemonListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreenRoot(
    onPokemonClick: () -> Unit,
    viewModel: PokemonListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is PokemonListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    PokemonListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is PokemonListAction.OnPokemonClick -> onPokemonClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun PokemonListScreen(
    state: PokemonListState,
    onAction: (PokemonListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column {
            Spacer(modifier = Modifier.height(48.dp))
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.pokemon) { pokemon ->
                    PokemonListItem(
                        pokemon = pokemon,
                        onClick = { onAction(PokemonListAction.OnPokemonClick(pokemon)) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}