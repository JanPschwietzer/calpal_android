package com.janpschwietzer.calpal.presentation.views.barcodescanner

import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
@Composable
fun BarcodeScannerScreen(
    navController: NavHostController,
    onBarcodeScanned: (String) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(navController.context) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                WindowInsets.statusBars.asPaddingValues()
            ),
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { previewView }
        ) { view ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(navController.context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(view.surfaceProvider)
                }

                val barcodeScanner = BarcodeScanning.getClient()
                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = InputImage.fromMediaImage(
                                    mediaImage,
                                    imageProxy.imageInfo.rotationDegrees
                                )
                                barcodeScanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        for (barcode in barcodes) {
                                            barcode.rawValue?.let { scannedValue ->
                                                onBarcodeScanned(scannedValue)
                                            }
                                        }
                                    }
                                    .addOnCompleteListener { imageProxy.close() }
                            }
                        }
                    }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalyzer
                )
            }, ContextCompat.getMainExecutor(navController.context))
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val overlayColor = Color.White.copy(alpha = 0.7f) // Halbtransparente weiße Farbe
            val scannerRect = Rect(
                center.x - 300f, // Breite des Scan-Fensters
                center.y - 150f, // Höhe des Scan-Fensters
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(R.string.go_back),
                    tint = Color.White
                )
            }
        }
    }



}

@PreviewLightDark
@Composable
private fun BarcodeScannerScreenPreview() {
    CalPalTheme {
        BarcodeScannerScreen(
            rememberNavController(),
            onBarcodeScanned = {}
        )
    }
}