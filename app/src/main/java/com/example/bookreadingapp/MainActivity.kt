package com.example.bookreadingapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapp.ui.BookReadingApp
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.objects.BottomNavBar
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModelFactory
import com.example.bookreadingapp.ui.objects.NavRail
import com.example.bookreadingapp.ui.objects.NavigationHost
import com.example.bookreadingapp.ui.objects.PermanentNavDrawer
import com.example.bookreadingapp.ui.objects.TopAppBar
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import com.example.bookreadingapp.ui.utils.AdaptiveNavigationType


class MainActivity : ComponentActivity() {
    private val downloadViewModel: DownloadViewModel by viewModels {
        DownloadViewModelFactory(this.applicationContext)
    }

    @ExperimentalMaterial3WindowSizeClassApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookReadingAppTheme {
                val windowSize = calculateWindowSizeClass(this)
                BookReadingApp(
                    windowSize = windowSize.widthSizeClass,
                    downloadViewModel = downloadViewModel
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 1200,
    heightDp = 800
)
@Composable
fun ReadingPreview() {
    BookReadingAppTheme {
        BookReadingApp(
            windowSize = WindowWidthSizeClass.Expanded,
            downloadViewModel = viewModel()
        )
    }
}