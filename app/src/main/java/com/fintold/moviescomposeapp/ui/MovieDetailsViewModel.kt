package com.fintold.moviescomposeapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.fintold.moviescomposeapp.models.Movie
import com.fintold.moviescomposeapp.models.fromJson

const val MOVIE_DISPLAYED = "movieDisplayed"

class MovieDetailsViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val newMovie: String = checkNotNull(savedStateHandle[MovieDetailsDestination.movieArg])
    var movieDisplayed: Movie by mutableStateOf(Movie())
        private set

    init {
        updateMovieDisplayed()
    }

    private fun updateMovieDisplayed() {
        movieDisplayed = newMovie.fromJson()
    }
}