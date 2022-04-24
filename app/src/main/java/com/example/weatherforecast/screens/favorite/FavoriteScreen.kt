package com.example.weatherforecast.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.model.entity.Favorite
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorite Cities",
            isFavoriteScreen = true,
            icon = Icons.Default.ArrowBack, isMainScreen = false,
            navController = navController) { navController.popBackStack() }
    }, backgroundColor = Color.LightGray) {

        Surface(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
            color = Color.Transparent) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                val list = favoriteViewModel.favList.collectAsState().value

                LazyColumn {
                    items(items = list) {
                        CityRow(it, navController, favoriteViewModel)
                    }
                }
            }
        }
    }

}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel,
) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .height(50.dp)
        .clickable {
            navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
        },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp), bottomStart = CornerSize(6.dp)),
        color = Color(0xFFFFFFFF)) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city,
                Modifier
                    .padding(4.dp)
                    .weight(10f),
                color = Color.Blue)
            Surface(Modifier
                .padding(0.dp)
                .weight(10f),
                shape = CircleShape,
                color = Color(0xFFFFC400)) {
                Text(text = favorite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption,
                    color = Color(0xFF0010F3),
                    textAlign = TextAlign.Center)
            }

            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete",
                modifier = Modifier
                    .clickable {
                        favoriteViewModel.deleteFavorite(favorite)
                    }
                    .weight(10f),
                tint = Color.Blue)
        }
    }
}