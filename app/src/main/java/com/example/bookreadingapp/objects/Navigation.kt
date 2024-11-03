package com.example.bookreadingapp.objects

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    modifier: Modifier,
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
    navController: NavHostController
) {
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        NavBarItems.BarItems.forEach{
                navItem -> NavigationBarItem(
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