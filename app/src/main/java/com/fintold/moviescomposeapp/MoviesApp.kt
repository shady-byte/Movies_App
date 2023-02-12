package com.fintold.moviescomposeapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fintold.moviescomposeapp.models.MoviesMenu
import com.fintold.moviescomposeapp.ui.navigation.MoviesNavHost
import com.fintold.moviescomposeapp.ui.theme.paleBlack
import com.fintold.moviescomposeapp.ui.theme.paleYellow

@Composable
fun MoviesApp(navController: NavHostController = rememberNavController()) {
    MoviesNavHost(navController = navController)
}

@Composable
fun MoviesTopAppBar(title: String, canNavigateUp: Boolean, navigateUp:() -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showMenu by rememberSaveable {
        mutableStateOf(false)
    }
    var categorySelected by rememberSaveable {
        mutableStateOf(MoviesMenu.PopularMoviesItem)
    }

    if(canNavigateUp) {
        TopAppBar(
            modifier = modifier,
        ) {
            Box(modifier = modifier
                .fillMaxWidth()
                .height(32.dp)) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowLeft,
                        modifier = Modifier.size(30.dp),
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
                Row(modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    } else {
        TopAppBar(
            title = {
                Text(text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            modifier = modifier
        )
    }
}

@Composable
fun MovieItem(@StringRes itemNameRes: Int, iconVector: ImageVector, categorySelected: MoviesMenu, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = iconVector,
            contentDescription = stringResource(id = itemNameRes),
            tint = if(categorySelected.nameRes == itemNameRes) paleYellow else Color.Gray
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(id = itemNameRes),
            fontWeight = FontWeight.SemiBold,
        )
    }
}


