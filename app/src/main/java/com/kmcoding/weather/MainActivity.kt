package com.kmcoding.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kmcoding.weather.ui.WeatherApp
import com.kmcoding.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                val windowSize =
                    with(LocalDensity.current) {
                        currentWindowSize().toSize().toDpSize()
                    }
                WeatherApp(windowSize = windowSize)
            }
        }
    }
}
