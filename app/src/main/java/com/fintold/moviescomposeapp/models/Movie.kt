package com.fintold.moviescomposeapp.models

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.fintold.moviescomposeapp.data.MoviesListPager
import com.google.gson.Gson
import com.squareup.moshi.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

data class Movie(
    val id: Int = 0,
    @Json(name = "original_title") val title: String = "",
    @Json(name = "overview") val plotSynopsis: String = "",
    @Json(name = "poster_path") val posterPath: String = "",
    @Json(name = "release_date") val releaseDate: String = "",
    @Json(name = "vote_average") val rating: Double = 0.0
)

fun Movie.toJson(): String {
    val url = URLEncoder.encode(posterPath,StandardCharsets.UTF_8.toString())
    val synopsis = URLEncoder.encode(plotSynopsis, StandardCharsets.UTF_8.toString())
    val movie = copy(posterPath = url, plotSynopsis = synopsis)

    return Gson().toJson(movie)

}

fun String.fromJson(): Movie {
    return Gson().fromJson(this.replace("+"," "), Movie::class.java)
}

fun String.toImageUrlWithLowRes(): String {
    val baseUrl = "https://image.tmdb.org/t/p/w185"
    return baseUrl + this
}

fun String.toImageUrlWithHighRes(): String {
    val baseUrl = "https://image.tmdb.org/t/p/w342"
    return baseUrl + this
}

fun String.toFormattedDate(): String {
    val year = this.split("-").first()
    val month = this.split("-")[1]
    val monthInString = Months.values().firstOrNull { it.number == month.toInt() }
    return "$monthInString $year"
}

fun LazyPagingItems<Movie>.toMoviesList(): List<Movie> {
    val movies = mutableListOf<Movie>()
    for(i in 0 until itemCount) {
        if(!movies.contains(this[i]))
            movies.add(this[i]!!)
    }
    return movies
}
