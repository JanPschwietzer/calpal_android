package com.janpschwietzer.calpal.presentation.components.camera

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toAndroidRectF
import androidx.compose.ui.unit.dp

@Composable
fun CameraOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val overlayColor = Color.White.copy(alpha = 0.7f)
        val scannerRect = Rect(
            center.x - 300f,
            center.y - 150f,
            center.x + 300f,
            center.y + 150f
        )

        drawRect(color = overlayColor)

        drawIntoCanvas { canvas ->
            val paint = Paint().asFrameworkPaint()
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            canvas.nativeCanvas.drawRect(scannerRect.toAndroidRectF(), paint)
        }

        drawRect(
            color = Color.White,
            style = Stroke(width = 4.dp.toPx()),
            topLeft = Offset(scannerRect.left, scannerRect.top),
            size = Size(scannerRect.width, scannerRect.height)
        )
    }
}