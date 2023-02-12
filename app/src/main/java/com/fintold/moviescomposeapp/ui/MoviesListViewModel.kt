package com.fintold.moviescomposeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fintold.moviescomposeapp.data.repositories.MoviesRepository
import com.fintold.moviescomposeapp.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesListViewModel(private val onlineMoviesRepository: MoviesRepository): ViewModel() {

    private val _moviesList = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val moviesList = _moviesList

    init {
        getMoviesWithPagination("Popular")
    }

    fun getMoviesWithPagination(moviesCategory: String) {
        viewModelScope.launch {
            onlineMoviesRepository.getMoviesWithPagination(category = moviesCategory)
                .cachedIn(viewModelScope)
                .collectLatest{
                    moviesList.value = it
                }
        }
    }
}