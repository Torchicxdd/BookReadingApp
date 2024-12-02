package com.example.bookreadingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookreadingapp.ui.BookReadingApp
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModelFactory
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import com.example.bookreadingapp.ui.viewmodels.AppViewModelFactory
import com.example.bookreadingapp.ui.viewmodels.BookViewModel
import com.example.bookreadingapp.ui.viewmodels.MainViewModel


class MainActivity : ComponentActivity() {
    private val downloadViewModel: DownloadViewModel by viewModels {
        DownloadViewModelFactory(this.applicationContext)
    }
    private val bookViewModel: BookViewModel by viewModels {
        AppViewModelFactory(this.application)
    }
    private val mainViewModel: MainViewModel by viewModels {
        AppViewModelFactory(this.application)
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
                    downloadViewModel = downloadViewModel,
                    bookViewModel = mainViewModel.bookViewModel
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
//        BookReadingApp(
//            windowSize = WindowWidthSizeClass.Expanded,
//            downloadViewModel = viewModel()
//        )
//        BookReadingApp(
//            windowSize = WindowWidthSizeClass.Expanded,
//            downloadViewModel = viewModel()
//        )
    }
}