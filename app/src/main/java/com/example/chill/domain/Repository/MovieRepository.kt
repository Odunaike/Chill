package com.example.chill.domain.Repository

import com.example.chill.domain.model.MovieItem

interface MovieRepository{
    suspend fun getMovieList(): MovieItem
}
