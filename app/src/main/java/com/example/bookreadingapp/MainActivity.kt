package com.example.bookreadingapp

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapp.objects.BottomNavBar
import com.example.bookreadingapp.objects.NavRail
import com.example.bookreadingapp.objects.NavigationHost
import com.example.bookreadingapp.objects.PermanentNavDrawer
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import com.example.bookreadingapp.utils.AdaptiveNavigationType

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3WindowSizeClassApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookReadingAppTheme {
                val windowSize = calculateWindowSizeClass(this)
                BookReadingApp(
                    windowSize = windowSize.widthSizeClass
                )
            }
        }
    }
}

@Composable
fun BookReadingApp(windowSize: WindowWidthSizeClass, modifier: Modifier = Modifier)  {
    val navController = rememberNavController()

    // Adaptive navigation type depending on screen size
    val adaptiveNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> AdaptiveNavigationType.BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> AdaptiveNavigationType.NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER
        else -> AdaptiveNavigationType.BOTTOM_NAVIGATION
    }

    Scaffold(
        content = { padding ->
            Row(Modifier.padding(padding)) {
                // Navigation rail if medium screen size
                if (adaptiveNavigationType == AdaptiveNavigationType.NAVIGATION_RAIL) {
                    NavRail(
                        navController,
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxHeight()
                    )
                }
                // Permanent Navigation Drawer is expanded screen size
                if (adaptiveNavigationType == AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER) {
                    PermanentNavDrawer(navController)
                }
                if (adaptiveNavigationType in listOf(AdaptiveNavigationType.NAVIGATION_RAIL, AdaptiveNavigationType.BOTTOM_NAVIGATION)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        NavigationHost(navController)
                    }
                }
            }
        },
        bottomBar = {
            // Bottom bar if compact screen size
            if (adaptiveNavigationType == AdaptiveNavigationType.BOTTOM_NAVIGATION) {
                BottomNavBar(navController)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookReadingAppTheme {
        BookReadingApp(
            windowSize = WindowWidthSizeClass.Compact
        )
    }
}