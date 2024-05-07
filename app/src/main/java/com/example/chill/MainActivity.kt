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
import com.example.chill.presentation.Authentication.AuthenticationViewModel
import com.example.chill.presentation.Authentication.LoginScreen
import com.example.chill.presentation.Authentication.SignupScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chill.presentation.Authentication.AuthState
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
                        val authViewModel: AuthenticationViewModel = viewModel() //I didn't use dagger to create viewmodel since I was not injecting any dependency in the viewmodel
                        NavHost(
                            navController = navController,
                            startDestination = ChillAppDestinations.Login.name, //the app starts at login
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
                            //added the screens for login and signup
                            composable(
                                route = ChillAppDestinations.Login.name
                            ){
                                LoginScreen(
                                    viewModel = authViewModel,
                                    onClickToSignUp = {
                                        navController.navigate(ChillAppDestinations.Signup.name)
                                        authViewModel.resetAuthUiState()
                                    },
                                    onClickLogin = {
                                        authViewModel.signIntoAccountWithEmail()
                                        authViewModel.resetAuthUiState()
                                        authViewModel.resetAuthState()
                                    },
                                    navigateToHome = {navController.navigate(ChillAppDestinations.Home.name)}
                                )
                            }
                            composable(
                                route = ChillAppDestinations.Signup.name
                            ){
                                SignupScreen(
                                    viewModel = authViewModel,
                                    onSignUp = {
                                        authViewModel.createAccountWithMail()
                                        navController.navigateUp()
                                        authViewModel.resetAuthUiState()
                                        authViewModel.resetAuthState()
                                    },
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