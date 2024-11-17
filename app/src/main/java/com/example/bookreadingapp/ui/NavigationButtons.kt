package com.example.bookreadingapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.Text

// Back to Bookshelf Button
@Composable
fun BackToBookshelfButton(navController: NavController) {
    NavigationButton(text = "Back to Bookshelf") {
        navController.navigate("bookshelf")
    }
}

// Go to Search Button
@Composable
fun GoToSearchButton(navController: NavController) {
    NavigationButton(text = "Go to Search") {
        navController.navigate("search")
    }
}

// Go to Reading Button
@Composable
fun GoToReadingButton(navController: NavController) {
    NavigationButton(text = "Go to Reading") {
        navController.navigate("reading")
    }
}

// Go to Table of Content Button
@Composable
fun GoToTableContentButton(navController: NavController) {
    NavigationButton(text = "Table of Content") {
        navController.navigate("content_table")
    }
}

// Generic reusable button for navigation
@Composable
private fun NavigationButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}