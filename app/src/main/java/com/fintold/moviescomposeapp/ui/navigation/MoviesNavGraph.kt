package com.fintold.moviescomposeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fintold.moviescomposeapp.models.Movie
import com.fintold.moviescomposeapp.ui.*
import java.io.Serializable

@Composable
fun MoviesNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = SplashDestination.route,
        modifier = modifier
    ) {
        //Splash screen of the app
        composable(SplashDestination.route) {
            SplashScreen(onTimeOut = { navController.navigate(MoviesListDestination.route) {
                popUpTo(SplashDestination.route) {
                    inclusive = true
                    saveState = true
                }
            }})
        }
        //Movies list screen which is the home page
        composable(route = MoviesListDestination.route) {
            MoviesListScreen(onMovieClicked = {
                navController.navigate("${MovieDetailsDestination.route}/${it}")
            })
        }
        //Movie details screen
        composable(route = MovieDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(name = MovieDetailsDestination.movieArg) {
                type = NavType.StringType
            })
        ) {
                MovieDetailsScreen(
                    onNavigateUp = { navController.navigateUp() },
                    onNavigateBack = { navController.popBackStack() },
                )
        }
    }
}