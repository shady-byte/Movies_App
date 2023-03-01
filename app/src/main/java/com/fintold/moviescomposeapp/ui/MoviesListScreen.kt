package com.fintold.moviescomposeapp.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fintold.moviescomposeapp.MoviesTopAppBar
import com.fintold.moviescomposeapp.R
import com.fintold.moviescomposeapp.models.*
import com.fintold.moviescomposeapp.ui.navigation.NavigationDestination
import com.fintold.moviescomposeapp.ui.theme.lightingYellow

object MoviesListDestination: NavigationDestination {
    override val route: String = "MoviesListScreen"
    override val titleRes: Int = R.string.movies_app
}

@Composable
fun MoviesListScreen(onMovieClicked:(String) -> Unit, modifier: Modifier = Modifier,
    viewModel: MoviesListViewModel = viewModel(factory = MoviesViewModelProvider.Factory),
) {
    val movieState: LazyPagingItems<Movie> = viewModel.moviesList.collectAsLazyPagingItems()
    var categorySelected by rememberSaveable {
        mutableStateOf(MoviesMenu.PopularMoviesItem)
    }
    val context = LocalContext.current

    Scaffold(topBar = { MoviesTopAppBar(
            title = stringResource(id = MoviesListDestination.titleRes),
            canNavigateUp = false,
        )}
    ) {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            MoviesSortRow(
                selected = categorySelected,
                onSelected = {
                    categorySelected = it
                    viewModel.getMoviesWithPagination(context.resources.getString(categorySelected.nameRes))
                },
            )
            when(movieState.loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingScreen(modifier = modifier.padding(it))
                }
                is LoadState.Error -> {
                    FailureScreen(modifier = modifier.padding(it))
                }
                else -> {
                    SuccessScreen(
                        movies = movieState.toMoviesList(),
                        onMovieClicked = onMovieClicked,
                        modifier = modifier.padding(it))
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.drawable.loading_img),
            contentDescription = stringResource(id = R.string.loading),
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun FailureScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.failed_to_load),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
//List<Movie>
fun SuccessScreen(movies: List<Movie>, onMovieClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp),
    ) {
        items(items = movies, key = {it.id}) { movie ->
            MovieCard(movie = movie,
                onMovieClicked = onMovieClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesSortRow(selected: MoviesMenu, onSelected:(MoviesMenu) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(
            selected = selected == MoviesMenu.PopularMoviesItem,
            onClick = { onSelected(MoviesMenu.PopularMoviesItem) },
            enabled = false,
            shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
            border = BorderStroke(
                ChipDefaults.OutlinedBorderSize,
                Color.White
            ),
            colors = ChipDefaults.outlinedFilterChipColors(
                backgroundColor = if(selected == MoviesMenu.PopularMoviesItem) Color.White else Color.Transparent,
                contentColor = Color.Black
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Favorite,
                    tint = if(selected == MoviesMenu.PopularMoviesItem) lightingYellow else Color.Gray,
                    modifier = Modifier.padding(start = 3.dp),
                    contentDescription = stringResource(id = MoviesMenu.PopularMoviesItem.nameRes)
                )
            },
            modifier = Modifier
                .height(37.dp)

        ) {
            Text(text = stringResource(id = MoviesMenu.PopularMoviesItem.nameRes),
                style = MaterialTheme.typography.body1,
                color = if(selected == MoviesMenu.PopularMoviesItem) Color.Black else Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(end = 3.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(15.dp))

        FilterChip(
            selected = selected == MoviesMenu.TopRatedMoviesItem,
            onClick = { onSelected(MoviesMenu.TopRatedMoviesItem) },
            enabled = false,
            shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
            border = BorderStroke(
                ChipDefaults.OutlinedBorderSize,
                Color.White
            ),
            colors = ChipDefaults.outlinedFilterChipColors(
                backgroundColor = if(selected == MoviesMenu.TopRatedMoviesItem) Color.White else Color.Transparent,
                contentColor = Color.Black
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Star,
                    tint = if(selected == MoviesMenu.TopRatedMoviesItem) lightingYellow else Color.Gray,
                    modifier = Modifier.padding(start = 3.dp),
                    contentDescription = stringResource(id = MoviesMenu.TopRatedMoviesItem.nameRes)
                )
            },
            modifier = Modifier
                .height(37.dp)

        ) {
            Text(text = stringResource(id = MoviesMenu.TopRatedMoviesItem.nameRes),
                style = MaterialTheme.typography.body1,
                color = if(selected == MoviesMenu.TopRatedMoviesItem) Color.Black else Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(end = 3.dp)
            )
        }
    }
}


@Composable
fun MovieCard(movie: Movie, onMovieClicked: (String) -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .padding(8.dp)
        .fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(movie.posterPath.toImageUrlWithLowRes())
            .crossfade(true)
            .build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentDescription = movie.title,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clickable { onMovieClicked(movie.toJson()) },
        )
    }
}

