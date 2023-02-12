package com.fintold.moviescomposeapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fintold.moviescomposeapp.data.MoviesListPager
import com.fintold.moviescomposeapp.data.network.MoviesApisService
import com.fintold.moviescomposeapp.models.Movie
import kotlinx.coroutines.flow.Flow

class OnlineMoviesRepository(private val moviesApisService: MoviesApisService): MoviesRepository {
    override suspend fun getMoviesWithPagination(category: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviesListPager(category = category, moviesApi = moviesApisService)
            }
        ).flow
    }
}