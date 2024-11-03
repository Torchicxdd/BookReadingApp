package com.example.bookreadingapp.objects

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.bookreadingapp.screens.Reading
import com.example.bookreadingapp.screens.Search
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookreadingapp.R


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
    navController: NavHostController,
    context: Context
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_medium))
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.logo_size))
                        .padding(top = dimensionResource(R.dimen.padding_medium)),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth()
    )
}