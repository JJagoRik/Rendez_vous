package com.example.rendez_vous_test.presentation.ui.components

import androidx.compose.foundation.Image
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.rendez_vous_test.domain.model.Movie
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import java.util.Locale

@Composable
fun MovieCard(
    movie: Movie,
    genre: String,
    onClick: (Int) -> Unit
){
    Card(
        modifier = Modifier
            .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.42f)
            .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.45f),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        onClick = { onClick(movie.id ?: 0) }
    ) {
        val scale = LocalConfiguration.current
            .screenWidthDp
            .coerceAtMost(LocalConfiguration.current.screenHeightDp)

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding((scale * 0.03).dp)
                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.3f)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.movieImage)
                        .memoryCacheKey(movie.id?.toString())
                        .diskCacheKey(movie.id?.toString())
                        .build()
                )

                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painter,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                            .placeholder(
                                visible = painter.state is AsyncImagePainter.State.Loading,
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
                                )
                            )
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding((scale * 0.02).dp)
                        .clip(RoundedCornerShape(6.dp))
                        .requiredHeight((scale * 0.06).dp)
                        .requiredWidth((scale * 0.09).dp)
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
                            fontSize = (scale * 0.04f).sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = movie.title ?: "No title found",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = (scale * 0.045f).sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = genre,
                    fontSize = (scale * 0.045f).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}