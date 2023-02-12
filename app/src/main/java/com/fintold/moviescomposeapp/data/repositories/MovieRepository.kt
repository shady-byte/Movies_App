package com.fintold.moviescomposeapp.data.repositories

import androidx.paging.PagingData
import com.fintold.moviescomposeapp.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMoviesWithPagination(category: String): Flow<PagingData<Movie>>
}