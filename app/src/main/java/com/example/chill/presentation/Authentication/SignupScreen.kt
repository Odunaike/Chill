package com.example.chill.presentation.Authentication


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignupScreen(
    viewModel: AuthenticationViewModel,
    onSignUp: () -> Unit
){

    val authState = viewModel.authState

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        //for username entry
        OutlinedTextField(
            value = viewModel.authenticationUiState.username,
            onValueChange = {
                viewModel.onUsernameChanged(it)
            },
            placeholder = { Text(text = "Enter your username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = viewModel.authenticationUiState.email,
            onValueChange = {
                viewModel.onEmailChanged(it)
            },
            placeholder = { Text(text = "Enter your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = viewModel.authenticationUiState.password,
            onValueChange = {
                viewModel.onPasswordChanged(it)
            },
            placeholder = { Text(text = "Enter your password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        ElevatedButton(
            onClick = {
                onSignUp()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        ) {
            Text(text = "Sign up")
        }
    }

}