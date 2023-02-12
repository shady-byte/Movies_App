package com.fintold.moviescomposeapp

import android.app.Application
import com.fintold.moviescomposeapp.data.AppContainer
import com.fintold.moviescomposeapp.data.DefaultAppContainer

class MoviesApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}