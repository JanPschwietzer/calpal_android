package com.janpschwietzer.calpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.janpschwietzer.calpal.presentation.navigation.AppNavGraph
import com.janpschwietzer.calpal.ui.theme.CalPalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalPalTheme {
                AppNavGraph()
            }
        }
    }
}