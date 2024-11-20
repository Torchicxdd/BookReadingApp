package com.example.bookreadingapp.ui.screens

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.books
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.utils.AdaptiveNavigationType

@Composable
fun Reading(
    context: Context,
    viewModel: AppViewModel,
    navController: NavController,
    adaptiveNavigationType: AdaptiveNavigationType
) {
    val bookTitleResId = viewModel.selectedBookTitleResId
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("reading_screen")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = context.getString(R.string.reading),
                style = MaterialTheme.typography.displayLarge
            )

            Switch(
                checked = viewModel.readingMode,
                onCheckedChange = { viewModel.updateReadingMode() },
                modifier = Modifier.testTag("reading_mode_button"))

            val book = books.find { it.title == bookTitleResId }

            if (book != null) {
                BookDisplay(
                    imageResourceId = book.imageResourceId,
                    titleResourceId = book.title
                )
            } else {
                // Handling the case where the book is not found
                Text(
                    text = context.getString(R.string.book_404),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Composable
fun BookDisplay(
    @DrawableRes imageResourceId: Int,
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        BookCover(imageResourceId)
        Text(
            text = stringResource(titleResourceId),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
        )
    }
}
