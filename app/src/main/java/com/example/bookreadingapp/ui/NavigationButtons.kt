package com.example.bookreadingapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.bookreadingapp.R

// Back to Bookshelf Button
@Composable
fun BackToBookshelfButton(navController: NavController) {
    NavigationButton(
        text = stringResource(id = R.string.back_to_bookshelf),
        testTag = "back_to_bookshelf_button"
    ) {
        navController.navigate("bookshelf")
    }
}

// Go to Search Button
@Composable
fun GoToSearchButton(navController: NavController) {
    NavigationButton(
        text = stringResource(id = R.string.go_to_search),
        testTag = "go_to_search_button"
    ) {
        navController.navigate("search")
    }
}

// Go to Reading Button
@Composable
fun GoToReadingButton(navController: NavController) {
    NavigationButton(
        text = stringResource(id = R.string.go_to_reading),
        testTag = "go_to_reading_button"
    ) {
        navController.navigate("reading")
    }
}

// Go to Table of Content Button
@Composable
fun GoToTableContentButton(navController: NavController) {
    NavigationButton(
        text = stringResource(id = R.string.go_to_table_of_content),
        testTag = "table_of_content_button"
    ) {
        navController.navigate("content_table")
    }
}

// Generic reusable button for navigation
@Composable
private fun NavigationButton(text: String, testTag: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.testTag(testTag)
    ) {
        Text(text)
    }
}