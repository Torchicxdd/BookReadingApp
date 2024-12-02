package com.example.bookreadingapp.ui.utils

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.bookreadingapp.R

// Go to Search Button
@Composable
fun GoToSearchButton(navigateToSearch: () -> Unit) {
    NavigationButton(text = stringResource(id = R.string.go_to_search), testTag = "go_to_search_button") {
        navigateToSearch()
    }
}

// Go to Reading Button
@Composable
fun GoToReadingButton(navigateToReading: () -> Unit) {
    NavigationButton(text = stringResource(id = R.string.go_to_reading), testTag = "go_to_reading_button") {
        navigateToReading()
    }
}

// Generic reusable button for navigation
@Composable
private fun NavigationButton(text: String, testTag: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.testTag(testTag)) {
        Text(text)
    }
}