package com.example.chill.presentation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chill.ChillAppDestinations
import com.example.chill.presentation.Account.AccountScreen
import com.example.chill.presentation.Home.HomeMoviesScreen
import com.example.chill.presentation.Home.HomeViewModel
import com.example.chill.presentation.Home.MovieDetailsScreen


sealed class Screens(val route: String, val icon:ImageVector)
object HomeScreen: Screens (route = ChillAppDestinations.Home.name, icon = Icons.Filled.Home)
object FavoritesScreen: Screens (route = ChillAppDestinations.Favorites.name, icon = Icons.Filled.Favorite )
object AccountScreen: Screens (route = ChillAppDestinations.Account.name, icon = Icons.Filled.AccountCircle)

val screens = listOf(HomeScreen, FavoritesScreen, AccountScreen)

@Composable
fun MainApp(
    viewModel: HomeViewModel
){
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ){
        NavHost(
            navController = navController,
            startDestination = ChillAppDestinations.Home.name,
            modifier = Modifier.padding(it)
        ){
            composable(
                route = ChillAppDestinations.Favorites.name
            ){

            }
            composable(
                route = ChillAppDestinations.Account.name
            ){
                AccountScreen()
            }
            composable(
                route = ChillAppDestinations.Home.name
            ){
                HomeMoviesScreen(
                    onItemSelected = {
                        viewModel.setClickedItem(result = it)
                        navController.navigate(ChillAppDestinations.Detail.name)
                    },
                    viewModel = viewModel
                )
            }
            composable(
                route = ChillAppDestinations.Detail.name
            ){
                MovieDetailsScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
private fun getCurrentRoute(navController: NavHostController): String? {
    val backStackEntry by navController.currentBackStackEntryAsState()
    return backStackEntry?.destination?.route
}


@Composable
fun BottomNavigationBar(navController: NavHostController){
    NavigationBar(
        modifier = Modifier.height(60.dp)
    ) {
        val currentRoute = getCurrentRoute(navController = navController)
        screens.forEach{
            NavigationBarItem(
                selected = it.route == currentRoute,
                onClick = {
                    navController.navigate(it.route)
                },
                icon = { Icon(imageVector = it.icon, contentDescription = null) }
            )
        }

    }
}