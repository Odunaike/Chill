package com.example.chill.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieItem(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)