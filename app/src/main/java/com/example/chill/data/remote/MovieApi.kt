package com.example.chill.data.remote
import com.example.chill.domain.model.MovieItem
import com.example.chill.domain.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie")
    suspend fun getMoviesList(
        @Query("api_key") apikey: String = API_KEY
    ): MovieItem
    companion object {
        const val API_KEY = "ba2d35fa2f4b7531a89c5c5ed73aa240"
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}