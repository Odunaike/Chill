package com.example.chill.presentation

import android.net.Uri

data class CurrentUser(
    val username: String? = "",
    val photoUri: String? = "",
    val userID: String? = null
)
