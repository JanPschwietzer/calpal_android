package com.janpschwietzer.calpal.presentation.views.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ChartModel
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import com.janpschwietzer.calpal.ui.theme.surfaceContainerLight

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    charts: List<ChartModel>,
    maxCalories: Int = 2000,
    size: Dp = 200.dp,
    strokeWidth: Dp = 50.dp
) {
    Column {
        val surfaceContainerColor = MaterialTheme.colorScheme.surfaceContainer

        Canvas(
            modifier = modifier
                .size(size)
                .padding(30.dp),
            onDraw = {
                var startAngle = -90f
                var sweepAngle: Float

                var totalSweepAngle = 0f

                charts.forEach { chart ->
                    sweepAngle = (chart.value.toFloat() / maxCalories) * 360
                    totalSweepAngle += sweepAngle

                    // Draw the arc
                    drawArc(
                        color = chart.color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidth.toPx(),
                            cap = StrokeCap.Butt,
                            join = StrokeJoin.Miter
                        )
                    )

                    startAngle += sweepAngle
                }

                sweepAngle = 360f - totalSweepAngle

                // Draw the arc
                drawArc(
                    color = surfaceContainerColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Butt,
                        join = StrokeJoin.Miter
                    )
                )
            }
        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            charts.forEach {
                LegendItem(
                    color = it.color,
                    description = it.description,
                    value = it.value
                )
            }
            LegendItem(
                color = surfaceContainerColor,
                description = stringResource(R.string.remaining),
                value = maxCalories - charts.sumOf { it.value }
            )
        }
    }
}

@Composable
private fun LegendItem(
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
            "${description}: ${value} kcal",
            fontSize = 12.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun PieChartViewPreview() {
    CalPalTheme {
        PieChart(
            modifier = Modifier.size(200.dp),
            charts = listOf(
                ChartModel(
                    value = 200,
                    color = MaterialTheme.colorScheme.primary,
                    description = stringResource(R.string.eaten)
                ),)
        )
    }
}