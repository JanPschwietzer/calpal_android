package com.janpschwietzer.calpal.presentation.views.barcodescanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.janpschwietzer.calpal.presentation.components.button.CloseButton
import com.janpschwietzer.calpal.presentation.components.camera.CameraOverlay
import com.janpschwietzer.calpal.presentation.components.camera.CameraPreviewView
import com.janpschwietzer.calpal.ui.theme.CalPalTheme

@Composable
fun BarcodeScannerScreen(
    navController: NavHostController,
    onBarcodeScanned: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    // Überprüfen, ob die Kamera-Berechtigung bereits erteilt wurde
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(navController.context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher für die Berechtigungsanfrage
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasCameraPermission = isGranted
        }

    // Wenn die Berechtigung noch nicht erteilt ist, Anfrage starten
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

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

        CloseButton(navController, onCloseClicked)
    }



}

@PreviewLightDark
@Composable
private fun BarcodeScannerScreenPreview() {
    CalPalTheme {
        BarcodeScannerScreen(
            rememberNavController(),
            onBarcodeScanned = {},
            onCloseClicked = {}
        )
    }
}