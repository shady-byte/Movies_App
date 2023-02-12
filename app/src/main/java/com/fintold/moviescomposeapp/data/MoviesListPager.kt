package com.fintold.moviescomposeapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fintold.moviescomposeapp.data.network.MoviesApisService
import com.fintold.moviescomposeapp.data.repositories.MoviesRepository
import com.fintold.moviescomposeapp.models.Movie

/**
 * pagination is not working properly, it loads all pages at once because pagination library not supporting
 LazyVerticalGrid until now, and I had to use LazyVerticalGrid because the task was to display the movies with
 a grid arrangement
 */
class MoviesListPager(
    private val category: String,
    private val moviesApi: MoviesApisService
): PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage  = params.key ?: 1
            val endOfPaginationReached = currentPage == 10

            val response = if(category == "Popular") {
                moviesApi.getPopularMovies(pageNumber = currentPage).results
            } else {
                moviesApi.getTopRatedMovies(pageNumber = currentPage).results
            }

            LoadResult.Page(
                data = response,
                prevKey = if(currentPage == 1) null else currentPage.dec(),
                nextKey = if(endOfPaginationReached) null else currentPage.inc()
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}