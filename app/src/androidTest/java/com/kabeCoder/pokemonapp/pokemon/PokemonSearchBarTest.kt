package com.kabeCoder.pokemonapp.pokemon

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.components.SearchBar
import org.junit.Rule
import org.junit.Test

class SearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchBar_whenTextChanged_callsOnQueryChanged() {
        var query = ""
        composeTestRule.setContent {
            SearchBar(query = query, onQueryChanged = { query = it })
        }

        composeTestRule.onNodeWithText("Search Pokémon").performTextInput("bulbasaur")

        assert(query == "bulbasaur")
    }
}