package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kabeCoder.pokemonapp.R
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import com.kabeCoder.pokemonapp.pokemon.presentation.models.PokemonUi
import com.kabeCoder.pokemonapp.pokemon.presentation.models.toPokemonUi
import com.kabeCoder.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonListItem(
    pokemonUi: PokemonUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current

    val contentColor = if (isSystemInDarkTheme()){
        Color.White
    } else {
        Color.Black
    }
    Row(
        modifier = modifier
            .clickable (onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(pokemonUi.imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = pokemonUi.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TeamsListItemPreview(){
    PokemonAppTheme {
        PokemonListItem(
            pokemonUi = previewPokemon,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

internal val previewPokemon = Pokemon(
    name = "pokemon",
    url = "url"
).toPokemonUi()