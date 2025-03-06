package com.kabeCoder.pokemonapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
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
        composable<Routes.PokemonList> {
            PokemonListScreenRoot(
                onPokemonClick ={ pokemonUrl ->
                    val route = Routes.PokemonDetail.createRoute(pokemonUrl)
                    navController.navigate(route) {
                        popUpTo(Routes.PokemonList) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable(
            route = "pokemonDetail/{pokemonDetailJson}",
            arguments = listOf(navArgument("pokemonDetailJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("pokemonDetailJson") ?: ""
            val pokemonDetail = Routes.PokemonDetail.fromRoute(json)
            PokemonDetailScreenRoot(pokemonDetail.url)
        }
    }
}

