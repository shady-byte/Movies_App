package com.fintold.moviescomposeapp.data

import com.fintold.moviescomposeapp.data.network.MoviesApisService
import com.fintold.moviescomposeapp.data.repositories.MoviesRepository
import com.fintold.moviescomposeapp.data.repositories.OnlineMoviesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


interface AppContainer {
    val onlineMoviesRepository: MoviesRepository
}

class DefaultAppContainer() : AppContainer {
    private val BASE_URL = "https://api.themoviedb.org/3/movie/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val moviesRetrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    private val moviesApiService: MoviesApisService by lazy {
        moviesRetrofit.create(MoviesApisService::class.java)
    }

    override val onlineMoviesRepository: MoviesRepository by lazy {
        OnlineMoviesRepository(moviesApisService = moviesApiService)
    }


}