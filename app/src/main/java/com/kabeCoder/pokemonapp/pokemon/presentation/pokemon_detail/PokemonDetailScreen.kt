package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kabeCoder.pokemonapp.R
import com.kabeCoder.pokemonapp.core.presentation.util.ObserveAsEvents
import com.kabeCoder.pokemonapp.core.presentation.util.toString
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonDetail
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonImage
import com.kabeCoder.pokemonapp.pokemon.domain.PokemonType
import com.kabeCoder.pokemonapp.pokemon.domain.TypeInfo
import com.kabeCoder.pokemonapp.ui.theme.PokemonAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailScreenRoot(
    pokemonUrl: String,
    viewModel: PokemonDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is PokemonDetailEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    LaunchedEffect(pokemonUrl) {
        viewModel.loadPokemonDetail(pokemonUrl)
    }

    PokemonDetailScreen(state = state)

}

@Composable
fun PokemonDetailScreen(
    state: PokemonDetailState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        state.pokemonDetail?.let { pokemon ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(pokemon.sprites.frontDefault)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                )
                Row {
                    Text(
                        text = stringResource(R.string.label_pokemon),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        pokemon.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
                Row {
                    Text(
                        text = stringResource(R.string.label_order),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        pokemon.order.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
                Row {
                    Text(
                        text = stringResource(R.string.label_type),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = pokemon.types.joinToString(", ") { it.type.name },
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
                Row {
                    Text(
                        text = stringResource(R.string.label_weight),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        pokemon.weight.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PokemonDetailScreenPreview() {
    PokemonAppTheme {
        PokemonDetailScreen(
            state = PokemonDetailState(
                pokemonDetail = PokemonDetail(
                    name = "Kabe",
                    order = 999,
                    sprites = PokemonImage(frontDefault = "https://example.com/image.png"),
                    types = listOf(
                        PokemonType(slot = 1, type = TypeInfo(name = "Human", url = "Android"))
                    ),
                    weight = 70
                )
            )
        )
    }
}
