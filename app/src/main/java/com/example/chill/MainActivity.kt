package com.example.chill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chill.presentation.Home.HomeViewModel
import com.example.chill.presentation.Home.MovieDetailsScreen
import com.example.chill.presentation.Home.HomeMoviesScreen
import com.example.chill.ui.theme.ChillTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChillTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold {
                        val navController = rememberNavController()
                        val viewModel: HomeViewModel = hiltViewModel()
                        NavHost(
                            navController = navController,
                            startDestination = ChillAppDestinations.Home.name,
                            modifier = Modifier.padding(it)
                        ){
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
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChillTheme {
        Greeting("Android")
    }
}