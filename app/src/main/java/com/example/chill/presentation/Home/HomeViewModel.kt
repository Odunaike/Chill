package com.example.chill.presentation.Home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chill.domain.Repository.MovieRepository
import com.example.chill.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: MovieRepository
): ViewModel(){
    var state by mutableStateOf(HomeState())

    init {
        viewModelScope.launch{
            getMovieList()
        }
    }
    suspend fun getMovieList(){
        state = state.copy(
            movieList = repository.getMovieList().results
        )
    }
    fun setClickedItem(result: Result){
        state = state.copy(
            clivkedItem = result
        )
    }
}