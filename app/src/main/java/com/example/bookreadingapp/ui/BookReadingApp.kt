package com.example.bookreadingapp.ui
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.objects.BottomNavBar
import com.example.bookreadingapp.ui.objects.NavRail
import com.example.bookreadingapp.ui.objects.NavigationHost
import com.example.bookreadingapp.ui.objects.PermanentNavDrawer
import com.example.bookreadingapp.ui.objects.TopAppBar
import com.example.bookreadingapp.ui.utils.AdaptiveNavigationType

/**
 * The main composable function that drives the UI layout and navigation based on screen size.
 *
 * @param windowSize The screen width size to adapt the UI components (Compact, Medium, Expanded).
 * @param viewModel The app's ViewModel for managing state.
 * @param navController The NavHostController for navigation management.
 * @param downloadViewModel The ViewModel responsible for handling downloads and data.
 */
@Composable
fun BookReadingApp(
    windowSize: WindowWidthSizeClass,
    viewModel: AppViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController(),
    downloadViewModel: DownloadViewModel
) {
    val context = LocalContext.current

    // Determine the type of navigation based on the window size
    val adaptiveNavigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> AdaptiveNavigationType.BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> AdaptiveNavigationType.NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER
        else -> AdaptiveNavigationType.BOTTOM_NAVIGATION
    }

    // Listen for destination changes to update the back navigation state
    navController.addOnDestinationChangedListener { _, _, _, ->
        viewModel.canNavigateBack = navController.previousBackStackEntry != null
    }

    Scaffold(
        topBar = {
            // Display the top bar only if not in reading mode
            if (!viewModel.readingMode) {
                TopAppBar(
                    canNavigateBack = viewModel.canNavigateBack,
                    navigateBack = { navController.navigateUp() }
                )
            }
        },
        content = { innerPadding ->
            AdaptiveContent(
                adaptiveNavigationType = adaptiveNavigationType,
                navController = navController,
                context = context,
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
                downloadViewModel = downloadViewModel
            )
        },
        bottomBar = {
            // Display the bottom navigation bar only for compact screens
            if (adaptiveNavigationType == AdaptiveNavigationType.BOTTOM_NAVIGATION && !viewModel.readingMode) {
                BottomNavBar(navController, context)
            }
        }
    )
}

/**
 * Displays the adaptive content layout based on the screen size and navigation type.
 *
 * @param adaptiveNavigationType The navigation type (bottom navigation, rail, or permanent drawer).
 * @param navController The NavHostController for managing navigation.
 * @param context The current context for accessing resources.
 * @param modifier Modifier to apply padding or layout behavior.
 * @param viewModel The ViewModel for managing the app state.
 * @param downloadViewModel The ViewModel for handling download and data operations.
 */
@Composable
fun AdaptiveContent(
    adaptiveNavigationType: AdaptiveNavigationType,
    navController: NavHostController,
    context: Context,
    modifier: Modifier,
    viewModel: AppViewModel,
    downloadViewModel: DownloadViewModel
) {
    Row(modifier = modifier) {
        // Display the navigation rail for medium-sized screens
        if (adaptiveNavigationType == AdaptiveNavigationType.NAVIGATION_RAIL && !viewModel.readingMode) {
            NavRailComponent(navController, context)
        }
        // Display the permanent navigation drawer for expanded screens
        if (adaptiveNavigationType == AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER) {
            PermanentNavDrawerComponent(navController, context, adaptiveNavigationType, viewModel, downloadViewModel)
        }
        // Display the main content for smaller screens or when in reading mode
        if (adaptiveNavigationType in listOf(AdaptiveNavigationType.NAVIGATION_RAIL, AdaptiveNavigationType.BOTTOM_NAVIGATION)) {
            ContentNavigationHost(navController, context, modifier, adaptiveNavigationType, viewModel, downloadViewModel)
        }
    }
}

/**
 * Displays the navigation rail for medium screen sizes.
 *
 * @param navController The NavHostController for managing navigation.
 * @param context The current context for accessing resources.
 */
@Composable
fun NavRailComponent(
    navController: NavHostController,
    context: Context
) {
    NavRail(
        navController,
        context,
        modifier = Modifier
            .fillMaxHeight()
    )
}

/**
 * Displays the permanent navigation drawer for expanded screen sizes.
 *
 * @param navController The NavHostController for managing navigation.
 * @param context The current context for accessing resources.
 * @param adaptiveNavigationType The type of adaptive navigation (rail or drawer).
 * @param viewModel The ViewModel for managing the app state.
 * @param downloadViewModel The ViewModel for handling download and data operations.
 */
@Composable
fun PermanentNavDrawerComponent(
    navController: NavHostController,
    context: Context,
    adaptiveNavigationType: AdaptiveNavigationType,
    viewModel: AppViewModel,
    downloadViewModel: DownloadViewModel
) {
    PermanentNavDrawer(
        navController,
        context,
        adaptiveNavigationType,
        modifier = Modifier
            .fillMaxSize(),
        viewModel = viewModel,
        downloadViewModel = downloadViewModel
    )
}

/**
 * Displays the content and navigation host for smaller screens or reading mode.
 *
 * @param navController The NavHostController for managing navigation.
 * @param context The current context for accessing resources.
 * @param modifier Modifier to apply layout behavior.
 * @param adaptiveNavigationType The type of adaptive navigation (rail or bottom navigation).
 * @param viewModel The ViewModel for managing the app state.
 * @param downloadViewModel The ViewModel for handling download and data operations.
 */
@Composable
fun ContentNavigationHost(
    navController: NavHostController,
    context: Context,
    modifier: Modifier,
    adaptiveNavigationType: AdaptiveNavigationType,
    viewModel: AppViewModel,
    downloadViewModel: DownloadViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        NavigationHost(
            navController,
            context,
            adaptiveNavigationType,
            Modifier,
            viewModel = viewModel,
            downloadViewModel = downloadViewModel
        )
    }
}