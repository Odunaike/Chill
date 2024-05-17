package com.example.chill.presentation.Authentication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlin.math.log


class AuthenticationViewModel: ViewModel() {

    val auth = Firebase.auth
    lateinit var database : FirebaseDatabase
    var user: FirebaseUser? = null

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
    fun onUsernameChanged(value: String) {
        authenticationUiState = authenticationUiState.copy(
            username = value
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
                    user = auth.currentUser
                    saveUsername(user) //rather than saving from the UI, I just did it here for easy access to the currentUser
                    resetAuthUiState()
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
                    authState = authState.copy(
                        isLoginSuccessful = true
                    )
                    Log.d("foo", "login successful")
                }else{
                    Log.d("foo", "login not successful")
                    authState = authState.copy(
                        isLoginSuccessful = false,
                        message = task.exception?.message
                    )
                }
            }
    }

    private fun saveUsername(user: FirebaseUser?){
        database = Firebase.database
        user?.let {
            val reference = database.reference
            val userID = it.uid
            reference.child("Users")
                .child(userID)
                .child("username")
                .setValue(authenticationUiState.username)
            Log.i("foo", "user is not null")
        }
    }

    //function to signOut
    fun signOut(){
        auth.signOut()
        authState = authState.copy(
            isLoginSuccessful = false
        )
        Log.d("foo", "sign out successful")
    }

    //the below function is necessary to clear the text when I navigate
    fun resetAuthUiState(){
        authenticationUiState = AuthenticationUiState()
    }
    fun resetAuthState(){
        authState = AuthState()
    }
}