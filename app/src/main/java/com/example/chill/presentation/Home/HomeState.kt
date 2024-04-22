package com.example.chill.presentation.Home

import com.example.chill.domain.model.Result

data class HomeState(
    var movieList: List<Result> = emptyList(),
    var clivkedItem: Result? = null
)

