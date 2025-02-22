package com.example.rendez_vous_test.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rendez_vous_test.presentation.MoviesViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movieId: Int,
    whichDataToUse: String,
    viewModel: MoviesViewModel
) {
    val initialMovie = viewModel.getMovieById(movieId, whichDataToUse)
    val movie = remember { mutableStateOf(initialMovie) }
    val isFavorite = remember { mutableStateOf(viewModel.isFavoriteMovie(initialMovie)) }
    val scale = LocalConfiguration.current
        .screenWidthDp
        .coerceAtMost(LocalConfiguration.current.screenHeightDp)
    val genre = viewModel.genres.value?.find { it.id == movie.value?.genreId }?.name ?: "Undetected"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.value?.movieImage)
                    .memoryCacheKey(movie.value?.id?.toString())
                    .diskCacheKey(movie.value?.id?.toString())
                    .build(),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .height(300.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.value?.title ?: "No film name",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = (scale * 0.055).sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height((scale * 0.04).dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = movie.value?.voteAvg.let { String.format(Locale.US, "%.1f", it) },
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = when (movie.value?.voteAvg ?: 0.0) {
                            in 0.0..5.0 -> Color.Red
                            in 5.0..7.0 -> Color.Gray
                            in 7.0..10.0 -> Color.Green
                            else -> Color.Transparent
                        },
                        fontSize = (scale * 0.045).sp
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = movie.value?.releaseDate ?: "No film name",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    fontSize = (scale * 0.045).sp
                )
            }

            Text(
                text = genre,
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                fontSize = (scale * 0.045).sp
            )

            Spacer(modifier = Modifier.height((scale * 0.04).dp))

            Text(
                text = movie.value?.overview ?: "No description available",
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .clickable {
                    if (!isFavorite.value) {
                        isFavorite.value = true
                        viewModel.addToFavorite(movie.value)
                    } else {
                        isFavorite.value = false
                        viewModel.removeFromFavorite(movie.value)
                    }
                }
                .background(if (isFavorite.value) Color.Red else Color.Gray),

            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isFavorite.value) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Add to Favorites",
                tint = Color.White
            )
        }
    }
}