package com.fintold.moviescomposeapp.data.network

import com.fintold.moviescomposeapp.BuildConfig
import com.fintold.moviescomposeapp.models.ResultOfMoviesApi
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY

interface MoviesApisService {
    //Get popular Movies
    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") ApiKey: String = API_KEY,
            @Query("page") pageNumber: Int): ResultOfMoviesApi
    //Get Top-rated Movies
    @GET("top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") ApiKey: String = API_KEY,
            @Query("page") pageNumber: Int): ResultOfMoviesApi
}