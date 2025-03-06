package com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabeCoder.pokemonapp.R

@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = { onQueryChanged(it) },
        label = { Text(stringResource(R.string.label_search_pokemon)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textStyle = TextStyle(fontSize = 18.sp),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.label_search_icon))
        },
        singleLine = true
    )
}