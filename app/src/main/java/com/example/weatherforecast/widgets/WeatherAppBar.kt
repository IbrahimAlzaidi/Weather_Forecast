package com.example.weatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.model.entity.Favorite
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.screens.favorite.FavoriteViewModel

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    isFavoriteScreen : Boolean = false,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val showIt = remember { mutableStateOf(false) }
    var message = ""
    var state = ""
    if (showDialog.value) ShowSettingDropDownMenu(showDialog = showDialog,
        navController = navController)

    TopAppBar(title = {
        Text(text = title,
            color = if (isFavoriteScreen) Color(0xFF002FFF) else MaterialTheme.colors.onSecondary,
            style = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 15.sp))
    },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "Search")
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
            if (isMainScreen) {
                val isAlreadyFavList =
                    favoriteViewModel.favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }
                if (isAlreadyFavList.isNullOrEmpty()) {
                    Icon(imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel
                                    .insertFavorite(Favorite(
                                        city = dataList[0],
                                        country = dataList[1]))
                                    .run {
                                        message = "Added to The Favorite"
                                        state = "add"
                                        showIt.value = true
                                    }
                            },
                        tint = Color.Red.copy(alpha = 0.6f))
                } else {
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel
                                    .deleteFavorite(Favorite(
                                        city = dataList[0],
                                        country = dataList[1]))
                                    .run {
                                        message = "Deleted to The Favorite"
                                        state = "del"
                                        showIt.value = true
                                    }
                            },
                        tint = Color.Red.copy(alpha = 0.6f))
                }
                ShowToast(context = context, state, message, showIt)
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation)
}

@Composable
fun ShowToast(context: Context, state: String, message: String, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        if (state == "del") Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        if (state == "add") Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController,
) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf("About", "Favorites", "Settings")
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)) {
        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(imageVector = when (text) {
                        "About" -> Icons.Default.Info
                        "Favorites" -> Icons.Default.Favorite
                        else -> Icons.Default.Settings
                    },
                        contentDescription = null,
                        tint = Color.LightGray)
                    Text(text = text,
                        modifier = Modifier.clickable {
                            navController.navigate(when (text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavoriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            })
                        }, fontWeight = FontWeight.W300)
                }
            }
        }
    }
}