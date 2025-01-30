package com.janpschwietzer.calpal.presentation.components

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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ChartModel
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun PieChartView(
    modifier: Modifier,
    charts: List<ChartModel>,
    size: Dp = 200.dp,
    strokeWidth: Dp = 50.dp
) {
    Column {
        Canvas(
            modifier = modifier
                .size(size)
                .padding(30.dp),
            onDraw = {
                var startAngle = -90f
                var sweepAngle: Float

                charts.forEach { chart ->
                    sweepAngle = (chart.percentage.toFloat() / 100) * 360

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
            }
        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            charts.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Canvas(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    ) {
                        drawCircle(
                            color = it.color,
                            radius = 10f
                        )
                    }
                    Text(
                        "${it.description}: ${it.value} kcal",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PieChartViewPreview() {
    CalPalTheme {
        PieChartView(
            modifier = Modifier.size(200.dp),
            charts = listOf(
                ChartModel(percentage = 10, value = 200, color = MaterialTheme.colorScheme.primary, description = stringResource(
                    R.string.eaten
                )
                ),
                ChartModel(percentage = 90, value = 1800, color = MaterialTheme.colorScheme.primaryContainer, description = stringResource(
                    R.string.remaining
                )
                ),
                )
        )
    }
}