package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import com.example.bookreadingapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.objects.Routes
import com.example.bookreadingapp.utils.AdaptiveNavigationType

@Composable
fun ContentTable(
    context: Context,
    viewModel: AppViewModel,
    navController: NavController,
    adaptiveNavigationType: AdaptiveNavigationType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("content_screen")
    ) {
        // Content section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            // Title and selected book information
            Text(text = context.getString(R.string.content), style = MaterialTheme.typography.displayLarge)
            Text(text = "Book Chosen: ${stringResource(viewModel.selectedBookTitleResId)}")

            // Navigation buttons for Bookshelf, Search, and Reading screens
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                BackToBookshelfButton(navController = navController)
                GoToSearchButton(navController = navController)
                GoToReadingButton(navController = navController)
            }
        }
    }
}

@Composable
fun BackToBookshelfButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Routes.Bookshelf.route) {
                popUpTo(Routes.ContentTable.route) { inclusive = true }
            }
        }
    ) {
        Text(text = "Back to Bookshelf", style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun GoToSearchButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Routes.Search.route) {
                popUpTo(Routes.ContentTable.route) { inclusive = true }
            }
        }
    ) {
        Text(text = "Go to Search", style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun GoToReadingButton(navController: NavController) {
    Button(
        onClick = {
            navController.navigate(Routes.Reading.route) {
                popUpTo(Routes.ContentTable.route) { inclusive = true }
            }
        }
    ) {
        Text(text = "Go to Reading", style = MaterialTheme.typography.labelSmall)
    }
}