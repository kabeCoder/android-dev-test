package com.kabeCoder.pokemonapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_detail.PokemonDetailScreenRoot
import com.kabeCoder.pokemonapp.pokemon.presentation.pokemon_list.PokemonListScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
   NavHost(
       navController = navController,
       startDestination = Routes.Pokemon
   ) {
       pokemonGraph(navController)
   }
}

private fun NavGraphBuilder.pokemonGraph(navController: NavHostController) {
    navigation<Routes.Pokemon>(
        startDestination = Routes.PokemonList,
    ) {
        composable<Routes.PokemonList>{
            PokemonListScreenRoot(
                onPokemonClick = {
                    navController.navigate(Routes.PokemonDetail) {
                        popUpTo(Routes.PokemonList) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable<Routes.PokemonDetail>{
            PokemonDetailScreenRoot()
        }
    }
}