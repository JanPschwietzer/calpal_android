package com.janpschwietzer.calpal.data.model

import androidx.compose.ui.graphics.Color

data class ChartModel(
    val percentage: Int,
    val value: Int,
    val color: Color,
    val description: String
)
