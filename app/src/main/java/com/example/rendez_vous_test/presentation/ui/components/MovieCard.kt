package com.example.rendez_vous_test.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rendez_vous_test.domain.model.Movie
import java.util.Locale

@Composable
fun MovieCard(
    movie: Movie,
    genre: String,
    onClick: (Int) -> Unit
){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.4f)
            .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.45f),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = { onClick(movie.id ?: 0) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.3f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.movieImage)
                        .memoryCacheKey(movie.id?.toString())
                        .diskCacheKey(movie.id?.toString())
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .requiredHeight(20.dp)
                        .requiredWidth(35.dp)
                        .background(
                            when (val voteAvg = movie.voteAvg ?: 0.0) {
                                in 0.0..5.0 -> Color.Red
                                in 5.0..7.0 -> Color.Gray
                                in 7.0..10.0 -> Color.Green
                                else -> Color.Transparent
                            }
                        )
                ) {
                    Text(
                        text = movie.voteAvg?.let { String.format(Locale.US, "%.1f", it) } ?: "0.0",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = movie.title ?: "No title found",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = genre,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}