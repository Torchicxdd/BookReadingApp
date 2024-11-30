package com.example.bookreadingapp.ui.objects

import NavBarItems
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookreadingapp.R
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel
import com.example.bookreadingapp.ui.screens.Bookshelf
import com.example.bookreadingapp.ui.screens.ContentTable
import com.example.bookreadingapp.ui.screens.Home
import com.example.bookreadingapp.ui.screens.Library
import com.example.bookreadingapp.ui.screens.Reading
import com.example.bookreadingapp.ui.screens.Search
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.utils.AdaptiveNavigationType


/**
 * Navigation Host for the application
 * Contains all the composables that can be navigated to
 */
@Composable
fun NavigationHost(
    navController: NavHostController,
    context: Context,
    modifier: Modifier,
    viewModel: AppViewModel,
    downloadViewModel: DownloadViewModel
) {
    NavHost(navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            Home()
        }
        composable(Routes.Library.route) {
            Library(
                libraryBooks = viewModel.libraryBooks,
                setupDownload = { url: String, dir: String -> downloadViewModel.setupDownload(url, dir) },
                downloadViewModel,
                updateCurrentDownloadingBook = { viewModel.updateCurrentDownloadingBook(it) },
                onDownloadCompleteLibrary = {viewModel.onDownloadCompleteLibrary()},
                onDownloadCompleteBookshelf = {viewModel.onDownloadCompleteBookshelf()},
            )
        }
        composable(Routes.Bookshelf.route) {
            Bookshelf(
                bookshelfBooks = viewModel.bookshelfBooks,
                updateBook = { viewModel.updateBook(it) },
                navigateToTableOfContents = { navController.navigate(Routes.ContentTable.route){
                    launchSingleTop = true
                    restoreState = true
                } },
                downloadViewModel,
                onDownloadCompleteBookshelf = {viewModel.onDownloadCompleteBookshelf()}
            )
        }
        composable(Routes.Search.route) {
            Search(
                book = viewModel.selectedBook,
                searchBarInput = viewModel.searchBarInput,
                updateSearchBar = { viewModel.updateSearchBarInput(it) },
                performSearch = { viewModel.performSearch() },
                searchResult = viewModel.searchResultText
            )
        }
        composable(Routes.ContentTable.route) {
            ContentTable(
                book = viewModel.selectedBook,
                navigateToSearch =  { navController.navigate(Routes.Search.route) },
                navigateToReading =  { navController.navigate(Routes.Reading.route) }
            )
        }
        composable(Routes.Reading.route) {
            Reading(
                book = viewModel.selectedBook,
                readingMode = viewModel.readingMode,
                toggleReadingMode =  { viewModel.readingMode = !viewModel.readingMode },
            )
        }

    }
}

/**
 * Bottom bar of the app
 * Contains buttons with all the NavBarItems in it
 * Used when window size is compact
 */
@Composable
fun BottomNavBar(
    navController: NavHostController,
    context: Context,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.testTag("nav_bar")
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        val barItems = NavBarItems.getBarItems(context)
        // Loops through all the NavBarItems and places them on the bottom bar
        barItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if (currentRoute != navItem.route) {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(imageVector = navItem.image, contentDescription = navItem.title)
                },
                label = { Text(text = navItem.title) },
                modifier = navItem.modifier
            )
        }
    }
}

/**
 * Navigation rail of the app
 * Contains buttons with all the NavBarItems in it
 * Used when window size is medium
 */
@Composable
fun NavRail(
    navController: NavHostController,
    context: Context,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val barItems = NavBarItems.getBarItems(context)

    NavigationRail(
        modifier = modifier.testTag("nav_rail")
    ) {
        Spacer(Modifier.weight(1f))
        // Loops through all the NavBarItems and places them on the bottom bar
        barItems.forEach { navItem ->
            NavigationRailItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = navItem.image, contentDescription = navItem.title)
                },
                modifier = navItem.modifier
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

/**
 * Navigation permanent drawer of the app
 * Contains buttons with all the NavBarItems in it
 * Used when window size is expanded
 */
@Composable
fun PermanentNavDrawer(
    navController: NavHostController,
    context: Context,
    adaptiveNavigationType: AdaptiveNavigationType,
    viewModel: AppViewModel,
    downloadViewModel: DownloadViewModel,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val barItems = NavBarItems.getBarItems(context)

    PermanentNavigationDrawer(
        drawerContent = {
            if (!viewModel.readingMode) {
                PermanentDrawerSheet {
                    Spacer(Modifier.weight(1f))
                    // Loops through all the NavBarItems and places them on the bottom bar
                    barItems.forEach { navItem ->
                            Box(
                                modifier = Modifier
                                    .padding(start = dimensionResource(R.dimen.padding_big))
                            ) {
                            NavigationDrawerItem(
                                selected = currentRoute == navItem.route,
                                onClick = {
                                    navController.navigate(navItem.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = navItem.image,
                                        contentDescription = navItem.title
                                    )
                                },
                                label = { Text(text = navItem.title) },
                                modifier = navItem.modifier
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
        },
        // Content on the screen when using nav drawer
        content = {
            Box(
                modifier = modifier
            ) {
                NavigationHost(
                    navController = navController,
                    context = context,
                    modifier = modifier.fillMaxSize(),
                    viewModel = viewModel,
                    downloadViewModel = downloadViewModel
                )
            }
        },
        modifier = Modifier.testTag("nav_drawer")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    canNavigateBack: Boolean,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Determine if dark theme is active
    val darkTheme = isSystemInDarkTheme()

    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.logo_size))
                        .padding(top = dimensionResource(R.dimen.spacer_padding)),
                    painter = painterResource(
                        id = if (darkTheme) R.drawable.dark_logo else R.drawable.light_logo
                    ),
                    contentDescription = null
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateBack,
                    modifier = modifier.testTag("Back_Button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
