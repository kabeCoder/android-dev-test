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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kabeCoder.pokemonapp.core.presentation.util.ObserveAsEvents
import com.kabeCoder.pokemonapp.core.presentation.util.toString
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components.PokemonListItem
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components.SearchBar
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components.previewPokemon
import com.kabeCoder.pokemonapp.ui.theme.PokemonAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreenRoot(
    onPokemonClick: (String) -> Unit,
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
                is PokemonListAction.OnPokemonClick -> onPokemonClick(action.url)
            }
        }
    )
}

@Composable
fun PokemonListScreen(
    state: PokemonListState,
    onAction: (PokemonListAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = koinViewModel()
) {
    var query by remember { mutableStateOf("") }

    val listState = rememberLazyListState()

    LaunchedEffect(listState.firstVisibleItemIndex) {
        if (!state.isLoading && listState.layoutInfo.visibleItemsInfo.isNotEmpty()) {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.last()
            if (lastVisibleItem.index == state.pokemon.size - 1 && state.hasMore) {
                viewModel.loadNextPage()
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(48.dp))
        SearchBar(query = query, onQueryChanged = { query = it })

        val filteredPokemon = state.pokemon.filter { pokemon ->
            pokemon.name.contains(query, ignoreCase = true)
        }

        if (state.isLoading && state.pokemon.isEmpty()) {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredPokemon) { pokemonUi ->
                    PokemonListItem(
                        pokemonUi = pokemonUi,
                        onClick = { onAction(PokemonListAction.OnPokemonClick(pokemonUi.url)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
        }

        if (state.isLoading && state.hasMore) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PokemonListScreenPreview() {
    PokemonAppTheme {
        PokemonListScreen(
            state = PokemonListState(
                pokemon = (1..10).map {
                    previewPokemon
                }
            ),
            onAction = {}
        )
    }
}