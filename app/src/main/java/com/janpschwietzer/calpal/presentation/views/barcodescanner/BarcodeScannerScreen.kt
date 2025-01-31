package com.janpschwietzer.calpal.presentation.views.barcodescanner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.presentation.components.button.CloseButton
import com.janpschwietzer.calpal.presentation.components.camera.CameraOverlay
import com.janpschwietzer.calpal.presentation.components.camera.CameraPreviewView
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun BarcodeScannerScreen(
    navController: NavHostController,
    onBarcodeScanned: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                WindowInsets.statusBars.asPaddingValues()
            ),
    ) {

        CameraPreviewView(
            context = navController.context,
            onBarcodeScanned = onBarcodeScanned
        )

        CameraOverlay()

        CloseButton(navController)
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