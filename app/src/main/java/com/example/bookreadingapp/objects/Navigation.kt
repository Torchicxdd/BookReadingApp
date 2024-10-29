package com.example.bookreadingapp.objects

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapp.screens.ContentTable
import com.example.bookreadingapp.screens.Home
import com.example.bookreadingapp.screens.Library
import com.example.bookreadingapp.screens.Reading

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            Home()
        }
        composable(Routes.Library.route) {
            Library()
        }
        composable(Routes.Reading.route) {
            Reading()
        }
        composable(Routes.ContentTable.route) {
            ContentTable()
        }
        composable(Routes.Reading.route) {
            Reading()
        }
    }
}