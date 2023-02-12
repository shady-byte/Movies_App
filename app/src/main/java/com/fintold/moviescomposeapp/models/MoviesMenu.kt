package com.fintold.moviescomposeapp.models

import com.fintold.moviescomposeapp.R

enum class MoviesMenu(val nameRes: Int){
    PopularMoviesItem(R.string.popular_movies),
    TopRatedMoviesItem(R.string.top_rated_movies)
}

enum class Months(val number: Int) {
    January(1),
    February(2),
    March(3),
    April(4),
    May(5),
    June(6),
    July(7),
    August(8),
    September(9),
    October(10),
    November(11),
    December(12)
}