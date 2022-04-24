package com.example.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforecast.screens.about.AboutScreen
import com.example.weatherforecast.screens.favorite.FavoriteScreen
import com.example.weatherforecast.screens.main.MainScreen
import com.example.weatherforecast.screens.main.MainViewModel
import com.example.weatherforecast.screens.search.SearchScreen
import com.example.weatherforecast.screens.settings.SettingsScreen
import com.example.weatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) { WeatherSplashScreen(navController = navController) }
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = city.toString())
            }

        }
        composable(WeatherScreens.SearchScreen.name) { SearchScreen(navController = navController) }
        composable(WeatherScreens.AboutScreen.name) { AboutScreen(navController = navController) }
        composable(WeatherScreens.FavoriteScreen.name) { FavoriteScreen(navController = navController) }
        composable(WeatherScreens.SettingsScreen.name) { SettingsScreen(navController = navController) }
    }

}