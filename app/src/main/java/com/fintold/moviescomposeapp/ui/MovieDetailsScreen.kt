package com.fintold.moviescomposeapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fintold.moviescomposeapp.R
import com.fintold.moviescomposeapp.ui.navigation.NavigationDestination
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fintold.moviescomposeapp.MoviesTopAppBar
import com.fintold.moviescomposeapp.models.Movie
import com.fintold.moviescomposeapp.models.toFormattedDate
import com.fintold.moviescomposeapp.models.toImageUrlWithHighRes
import com.fintold.moviescomposeapp.ui.theme.lightingYellow
import com.fintold.moviescomposeapp.ui.theme.paleGray

object MovieDetailsDestination: NavigationDestination {
    override val route: String = "MovieDetailsScreen"
    override val titleRes: Int = R.string.movie_details
    const val movieArg = MOVIE_DISPLAYED
    val routeWithArgs = "${route}/{$movieArg}"
}

@Composable
fun MovieDetailsScreen(onNavigateUp:() -> Unit, onNavigateBack:() -> Unit, modifier: Modifier = Modifier,
      viewModel: MovieDetailsViewModel = viewModel(factory = MoviesViewModelProvider.Factory)
) {
    BackHandler {
        onNavigateBack()
    }
    val movieDisplayed = viewModel.movieDisplayed

    Scaffold(
        topBar = { MoviesTopAppBar(title = stringResource(id = R.string.movie_details),
            canNavigateUp = true,
            navigateUp = onNavigateUp)
        }
    ) {
        MovieDetailsBody(movie = movieDisplayed, modifier = modifier.padding(it))
    }

}

@Composable
fun MovieDetailsBody(movie: Movie, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp, bottom = 4.dp, top = 4.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        MoviePosterCard(movie = movie)
        Spacer(modifier = Modifier.height(16.dp))
        MovieTitle(movieTitle = movie.title)
        Spacer(modifier = Modifier.height(5.dp))
        MovieDetails(movie = movie)
        Spacer(modifier = Modifier.height(24.dp))
        MovieSynopsis(movieSynopsis = movie.plotSynopsis)
    }
}

@Composable
fun MovieSynopsis(movieSynopsis: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(id = R.string.synopsis),
            style = MaterialTheme.typography.h6
        )
        Text(text = movieSynopsis, color = paleGray, fontSize = 20.sp)
    }
}

@Composable
fun MovieDetails(movie: Movie, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = movie.releaseDate.toFormattedDate(), color = paleGray)
        Spacer(modifier = Modifier.width(10.dp))
        Divider(modifier = Modifier
            .height(12.dp)
            .width(1.dp),
            color = Color.White,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = movie.rating.toString(), color = paleGray)
        Spacer(modifier = Modifier.width(5.dp))
        Icon(imageVector = Icons.Filled.Star,
            tint = lightingYellow,
            contentDescription = movie.rating.toString(),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun MovieTitle(movieTitle: String, modifier: Modifier = Modifier) {
    Text(text = movieTitle,
        style = MaterialTheme.typography.h4,
        modifier = modifier
    )
}


@Composable
fun MoviePosterCard(movie: Movie, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(1f / 1.1f, false),
        elevation = 0.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(movie.posterPath.toImageUrlWithHighRes())
            .crossfade(true)
            .build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            contentDescription = movie.title
        )
    }
}

