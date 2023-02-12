package com.fintold.moviescomposeapp.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.fintold.moviescomposeapp.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.fintold.moviescomposeapp.ui.navigation.NavigationDestination

object SplashDestination: NavigationDestination {
    override val route: String = "SplashScreen"
    override val titleRes: Int = R.string.movies_app
}

@Composable
fun SplashScreen(onTimeOut:() -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(Unit) {
            delay(1.seconds)
            onTimeOut()
        }
        Image(painter = painterResource(id = R.drawable.popcorn_image),
            contentDescription = stringResource(id = R.string.launching_description)
        )
    }
}