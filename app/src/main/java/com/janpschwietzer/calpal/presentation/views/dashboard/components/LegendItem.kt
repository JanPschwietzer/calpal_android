package com.janpschwietzer.calpal.presentation.views.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LegendItem(
    color: Color,
    description: String,
    value: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Canvas(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            drawCircle(
                color = color,
                radius = 10f
            )
        }
        Text(
            "$description: $value kcal",
            fontSize = 12.sp
        )
    }
}