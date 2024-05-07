package com.example.chill.presentation.Authentication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class AuthenticationViewModel: ViewModel() {

    val auth = Firebase.auth

    var authenticationUiState by mutableStateOf(
        AuthenticationUiState()
    )
    var authState by mutableStateOf(
        AuthState()
    )

    fun onEmailChanged(value : String){
        authenticationUiState = authenticationUiState.copy(
            email = value
        )
    }
    fun onPasswordChanged(value : String){
        authenticationUiState = authenticationUiState.copy(
            password = value
        )
    }

    fun createAccountWithMail(){
        val email = authenticationUiState.email
        val password = authenticationUiState.password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.d("foo", "signup successful")
                    authState = authState.copy(
                        isSignupuccessful = true
                    )
                }else{
                    Log.d("foo", "signup failed")
                    authState = authState.copy(
                        isSignupuccessful = false,
                        message = task.exception?.message
                    )
                }
            }
    }
    fun signIntoAccountWithEmail(){
        val email = authenticationUiState.email
        val password = authenticationUiState.password
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.d("foo", "login successful")
                    authState = authState.copy(
                        isLoginSuccessful = true
                    )
                }else{
                    Log.d("foo", "login successful")
                    authState = authState.copy(
                        isLoginSuccessful = false,
                        message = task.exception?.message
                    )
                }
            }
    }

    //the below function is necessary to clear the text when I navigate
    fun resetAuthUiState(){
        authenticationUiState = AuthenticationUiState()
    }
    fun resetAuthState(){
        authState = AuthState()
    }
}