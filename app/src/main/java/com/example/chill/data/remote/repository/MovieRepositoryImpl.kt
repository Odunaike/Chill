package com.example.chill.data.remote.repository

import android.app.Application
import com.example.chill.data.remote.MovieApi
import com.example.chill.domain.Repository.MovieRepository
import com.example.chill.domain.model.MovieItem
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val api: MovieApi
): MovieRepository {
    override suspend fun getMovieList(): MovieItem{
        return api.getMoviesList()
    }
}