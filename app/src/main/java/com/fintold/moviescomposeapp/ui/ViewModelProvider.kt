package com.fintold.moviescomposeapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fintold.moviescomposeapp.MoviesApplication

object MoviesViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            MoviesListViewModel(movieApplication().container.onlineMoviesRepository)
        }
        initializer {
            MovieDetailsViewModel(this.createSavedStateHandle())
        }
    }
}

fun CreationExtras.movieApplication(): MoviesApplication =
    (this[APPLICATION_KEY] as MoviesApplication)