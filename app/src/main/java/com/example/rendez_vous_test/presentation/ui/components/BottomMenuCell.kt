package com.example.rendez_vous_test.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.ui.graphics.ColorFilter

@Composable
fun RowScope.BottomMenuCell(
    image: Int,
    description: String,
    isActive: Boolean,
    onClick: () -> Unit
){
    var isColored = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable {
                isColored.value = !isColored.value
                onClick()
                       },
        contentAlignment = Alignment.TopCenter
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .requiredHeight(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = image
                    ),
                    contentDescription = description,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp),
                    colorFilter = if (isActive) {
                        ColorFilter.tint(Color.Red)
                    } else {
                        ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    }
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}