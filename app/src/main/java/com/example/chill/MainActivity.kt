package com.example.chill

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chill.presentation.Authentication.AuthenticationViewModel
import com.example.chill.presentation.Authentication.LoginScreen
import com.example.chill.presentation.Authentication.SignupScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chill.presentation.Home.HomeViewModel
import com.example.chill.presentation.MainApp
import com.example.chill.ui.theme.ChillTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_sign_in_state")
val IS_SIGNED_IN  = booleanPreferencesKey("is_signed_in")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //reading from datastore
        val isSignedInFlow: Flow<Boolean> = this.dataStore.data
            .catch {
                if (it is IOException){
                    Log.e("foo", "error reading preferences", it)
                    emit(emptyPreferences())
                }else{
                    throw it
                }
            }
            .map { preferences ->
                preferences[IS_SIGNED_IN] ?: false
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

        setContent {
            ChillTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val isSignedIn = isSignedInFlow.collectAsState(initial = false).value

                    Scaffold {
                        val navController = rememberNavController()
                        val homeViewModel: HomeViewModel = hiltViewModel()
                        val authViewModel: AuthenticationViewModel = viewModel() //I didn't use dagger to create viewmodel since I was not injecting any dependency in the viewmodel
                        NavHost(
                            navController = navController,
                            startDestination = if(isSignedIn)  //get the signed in state from data store
                                ChillAppDestinations.MainApp.name
                            else
                                ChillAppDestinations.Login.name, //the app starts at login
                            modifier = Modifier.padding(it)
                        ){
                            composable(
                                route = ChillAppDestinations.MainApp.name
                            ){
                                MainApp(
                                    homeViewModel = homeViewModel,
                                    authenticationViewModel = authViewModel
                                ){
                                    navController.navigate(ChillAppDestinations.Login.name){
                                        popUpTo(ChillAppDestinations.MainApp.name){
                                            inclusive = true
                                        }
                                    }
                                    // save isSignedIn as false in datastore
                                    lifecycleScope.launch {
                                        saveIsSignedIn(false)
                                    }
                                }
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
                                        if (authViewModel.authState.isLoginSuccessful){
                                            navController.navigate(ChillAppDestinations.MainApp.name){
                                                popUpTo(ChillAppDestinations.Login.name){
                                                    inclusive = true
                                                }
                                            }
                                        }
                                        lifecycleScope.launch {
                                            saveIsSignedIn(authViewModel.authState.isLoginSuccessful)
                                        }
                                        Log.e("foo", isSignedIn.toString())
                                    },
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
                                        //authViewModel.resetAuthUiState() commented this because it runs before the username is saved to the server, I put it in the sign up function
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
    /** write to the data store*/
    suspend fun saveIsSignedIn(isSignedIn: Boolean){
        this.dataStore.edit { preferences ->
            preferences[IS_SIGNED_IN] = isSignedIn
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