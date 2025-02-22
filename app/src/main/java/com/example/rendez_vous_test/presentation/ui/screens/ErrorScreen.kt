package com.example.rendez_vous_test.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorScreen(retryAction: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.8f)
        ) {
            val textScaleFactor = LocalConfiguration.current.screenWidthDp.coerceAtMost(LocalConfiguration.current.screenHeightDp) * 0.05f
            Text(
                text = "У нас что-то пошло не так. Попробуйте обновить",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = textScaleFactor.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenWidthDp.dp * 0.05f))

        Button(
            onClick = retryAction,
            modifier = Modifier
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.3f)
                .requiredHeight(LocalConfiguration.current.screenHeightDp.dp * 0.07f),
            contentPadding = PaddingValues(0.dp)
        ) {
            val textScaleFactor = LocalConfiguration.current.screenWidthDp.coerceAtMost(LocalConfiguration.current.screenHeightDp) * 0.045f
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Обновить",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = textScaleFactor.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}