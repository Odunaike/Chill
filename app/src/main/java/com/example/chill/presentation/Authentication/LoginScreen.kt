package com.example.chill.presentation.Authentication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun LoginScreen(
    viewModel: AuthenticationViewModel,
    onClickToSignUp: () -> Unit,
    onClickLogin: () -> Unit,
    navigateToHome: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = viewModel.authenticationUiState.email,
            onValueChange = {
                viewModel.onEmailChanged(it)
            },
            placeholder = {Text(text = "Enter your email")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = viewModel.authenticationUiState.password,
            onValueChange = {
                viewModel.onPasswordChanged(it)
            },
            placeholder = {Text(text = "Enter your password")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        ElevatedButton(
            onClick = {
                      onClickLogin()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        ) {
            Text(text = "Login")
        }
        Row {
            Text(text = "Do not have an account?")
            Text(
                text = "sign up",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = {
                            onClickToSignUp()
                        }
                    )

            )
        }
        ElevatedButton(
            onClick = {
                //todo implement google sign in
            }
        ) {
            Text(text = "sign in with google")
        }
    }
    LaunchedEffect(key1 = viewModel.authState.isLoginSuccessful){
        if(viewModel.authState.isLoginSuccessful){
            navigateToHome()
        }else{
            Log.d("foo", "there is a problem")
        }
    }
}