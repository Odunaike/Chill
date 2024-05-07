package com.example.chill.presentation.Authentication

data class AuthState(
    val isSignupuccessful: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val message: String? = null
)