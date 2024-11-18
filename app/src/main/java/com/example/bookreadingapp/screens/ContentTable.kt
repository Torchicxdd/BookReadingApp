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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.utils.AdaptiveNavigationType
import com.example.bookreadingapp.ui.GoToReadingButton
import com.example.bookreadingapp.ui.GoToSearchButton

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
            Text(
                text = stringResource(
                    R.string.book_chosen,
                    stringResource(viewModel.selectedBookTitleResId)
                )
            )

            // Navigation buttons for Bookshelf, Search, and Reading screens
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                GoToSearchButton(navController = navController)
                GoToReadingButton(navController = navController)
            }
        }
    }
}