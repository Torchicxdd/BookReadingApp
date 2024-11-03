package com.example.bookreadingapp

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapp.objects.BottomNavBar
import com.example.bookreadingapp.objects.NavigationHost
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme

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

    val adaptiveNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            // Screen State
        }
        WindowWidthSizeClass.Medium -> {
            // Screen State
        }
        WindowWidthSizeClass.Expanded -> {
            // Screen State
        }
        else -> {
            // Screen State
        }
    }

    Scaffold(
        content = {padding ->
            Column(Modifier.padding(padding)) {
                NavigationHost(navController)
            } },
        bottomBar = { BottomNavBar(navController) }
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