package com.example.bookreadingapp.objects

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import NavBarItems
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.bookreadingapp.screens.ContentTable
import com.example.bookreadingapp.screens.Home
import com.example.bookreadingapp.screens.Library
import com.example.bookreadingapp.screens.Bookshelf
import com.example.bookreadingapp.screens.Reading
import com.example.bookreadingapp.screens.Search
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookreadingapp.R
import com.example.bookreadingapp.utils.AdaptiveNavigationType


@Composable
fun NavigationHost(
    navController: NavHostController,
    context: Context,
    adaptiveNavigationType: AdaptiveNavigationType,
    modifier: Modifier,
    viewModel: AppViewModel = viewModel(),
    downloadViewModel: DownloadViewModel
) {
    NavHost(navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            Home(context, viewModel, adaptiveNavigationType)
        }
        composable(Routes.Library.route) {
            Library(context, viewModel, navController, adaptiveNavigationType, downloadViewModel)
        }
        composable(Routes.Bookshelf.route) {
            Bookshelf(context, viewModel, navController, adaptiveNavigationType)
        }
        composable(Routes.Search.route) {
            Search(context, viewModel, navController, adaptiveNavigationType)
        }
        composable(Routes.ContentTable.route) {
            ContentTable(context, viewModel, navController, adaptiveNavigationType)
        }
        composable(Routes.Reading.route) {
            Reading(context, viewModel, navController, adaptiveNavigationType)
        }

    }
}


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

@Composable
fun PermanentNavDrawer(
    navController: NavHostController,
    context: Context,
    adaptiveNavigationType: AdaptiveNavigationType,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = viewModel(),
<<<<<<< HEAD
=======
    downloadViewModel: DownloadViewModel,
    modifier: Modifier = Modifier
>>>>>>> e86cb02 (Download and extract file on book selection)
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val barItems = NavBarItems.getBarItems(context)

    PermanentNavigationDrawer(
        drawerContent = {
            if (!viewModel.readingMode) {
                PermanentDrawerSheet {
                    Spacer(Modifier.weight(1f))
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
        content = {
            Box(
                modifier = modifier
            ) {
                NavigationHost(
                    navController,
                    context,
                    adaptiveNavigationType,
                    modifier = modifier.fillMaxSize(),
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
