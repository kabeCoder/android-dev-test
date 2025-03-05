package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabeCoder.pokemonapp.pokemon.domain.Pokemon
import com.kabeCoder.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
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
//        Icon(
//            imageVector = ImageVector.vectorResource(id = teamsUi.iconRes),
//            contentDescription = teamsUi.name,
//            tint = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.size(85.dp)
//        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = pokemon.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = pokemon.url,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
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
            pokemon = previewPokemon,
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

internal val previewPokemon = Pokemon(
    name = "pokemon",
    url = "url"
)