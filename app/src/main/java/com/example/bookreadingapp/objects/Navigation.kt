package com.example.bookreadingapp.objects

import NavBarItems
import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookreadingapp.screens.ContentTable
import com.example.bookreadingapp.screens.Home
import com.example.bookreadingapp.screens.Library
import com.example.bookreadingapp.screens.Reading
import com.example.bookreadingapp.screens.Search
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    context: Context,
    viewModel: AppViewModel = viewModel()
) {
    NavHost(navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            Home(context, viewModel)
        }
        composable(Routes.Library.route) {
            Library(context, viewModel)
        }
        composable(Routes.Search.route) {
            Search(context, viewModel)
        }
        composable(Routes.ContentTable.route) {
            ContentTable(context, viewModel)
        }
        composable(Routes.Reading.route) {
            Reading(context, viewModel)
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    context: Context,
    modifier: Modifier = Modifier,
) {
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        val barItems = NavBarItems.getBarItems(context)

        barItems.forEach { navItem ->
            NavigationBarItem(
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
                label = { Text(text = navItem.title) }
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

    NavigationRail {
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
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun PermanentNavDrawer(
    navController: NavHostController,
    context: Context,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val barItems = NavBarItems.getBarItems(context)

    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.padding(8.dp)
            ) {
                Spacer(Modifier.weight(0.5f))
                barItems.forEach { navItem ->
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
                            Icon(imageVector = navItem.image, contentDescription = navItem.title)
                        },
                        label = { Text(text = navItem.title) }
                    )
                }
                Spacer(Modifier.weight(1f))
            }
        },
        content = { NavigationHost(navController, context) },
        modifier = modifier
    )
}