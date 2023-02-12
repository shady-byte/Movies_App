package com.fintold.moviescomposeapp.models

data class ResultOfMoviesApi(
    val page: Int,
    val results: List<Movie>
)
