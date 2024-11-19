package com.example.bookreadingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.objects.BottomNavBar
import com.example.bookreadingapp.download.DownloadViewModel
import com.example.bookreadingapp.objects.DownloadViewModelFactory
import com.example.bookreadingapp.objects.NavRail
import com.example.bookreadingapp.objects.NavigationHost
import com.example.bookreadingapp.objects.PermanentNavDrawer
import com.example.bookreadingapp.objects.TopAppBar
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import com.example.bookreadingapp.utils.AdaptiveNavigationType

class MainActivity : ComponentActivity() {
    private val downloadViewModel: DownloadViewModel by viewModels {
        DownloadViewModelFactory(this.applicationContext) // Use application context to prevent memory leaks
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

@Composable
fun TestFileDownload(downloadViewModel: DownloadViewModel) {
    val directoryContents by downloadViewModel.directoryContents.observeAsState(emptyList())

    val urlList = stringArrayResource(R.array.download)

    Column (modifier = Modifier.padding(100.dp)){
        directoryContents.forEach { dirName ->
            Text(dirName)
        }

        Button(onClick = {
            for (url in urlList) {
                downloadViewModel.setupDownload(url,
                    "${url.substringAfterLast("/").replace(".zip", "")}-directory")
            }
        }) {
            Text("Download File")
        }
        Button(onClick = {
            downloadViewModel.confirmDeletion("TestDownloadDir2")
        }) {
            Text("Delete Directory")
        }
    }
}

@Composable
fun BookReadingApp(
    windowSize: WindowWidthSizeClass,
    viewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    downloadViewModel: DownloadViewModel
)  {
    val context = LocalContext.current

    // Adaptive navigation type depending on screen size
    val adaptiveNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> AdaptiveNavigationType.BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> AdaptiveNavigationType.NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER
        else -> AdaptiveNavigationType.BOTTOM_NAVIGATION
    }

    // Add a listener to navController which changes the canNavigateBack variable in viewmodel
    navController.addOnDestinationChangedListener { _, _, _, ->
        viewModel.canNavigateBack = navController.previousBackStackEntry != null
    }

    Scaffold(
        topBar = {
            if(!viewModel.readingMode) {
                TopAppBar(
                    canNavigateBack = viewModel.canNavigateBack,
                    navigateBack = { navController.navigateUp() }
                )
            }
        },
        content = { padding ->
            Row {
                // Navigation rail if medium screen size
                if (adaptiveNavigationType == AdaptiveNavigationType.NAVIGATION_RAIL
                    && !viewModel.readingMode) {
                    NavRail(
                        navController,
                        context,
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxHeight()
                    )
                }
                // Permanent Navigation Drawer is expanded screen size
                if (adaptiveNavigationType == AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER) {
                    PermanentNavDrawer(
                        navController,
                        context,
                        adaptiveNavigationType,
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        downloadViewModel = downloadViewModel)
                }
                if (adaptiveNavigationType in listOf(AdaptiveNavigationType.NAVIGATION_RAIL, AdaptiveNavigationType.BOTTOM_NAVIGATION)) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        NavigationHost(
                            navController,
                            context,
                            adaptiveNavigationType,
                            Modifier
                                .padding(padding),
                            downloadViewModel = downloadViewModel
                        )
                    }
                }
            }
        },
        bottomBar = {
            // Bottom bar if compact screen size
            if (adaptiveNavigationType == AdaptiveNavigationType.BOTTOM_NAVIGATION
                && !viewModel.readingMode) {
                BottomNavBar(navController, context)
            }
        }
    )
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