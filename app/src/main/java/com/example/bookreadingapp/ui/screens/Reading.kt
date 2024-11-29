package com.example.bookreadingapp.ui.screens

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.books

/**
 * Reading screen containing the book to read
 */
@Composable
fun Reading(
    @StringRes readingScreenTitle: Int,
    @StringRes bookNotFoundText: Int,
    book: Book?,
    toggleReadingMode: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                toggleReadingMode()
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .testTag("reading_screen")
        ) {
            Text(
                text = stringResource(readingScreenTitle),
                style = MaterialTheme.typography.displayLarge
            )

            if (book != null) {
                BookDisplay(
                    imageResourceId = book.imageResourceId,
                    titleResourceId = book.title
                )
            } else {
                // Handling the case where the book is not found
                Text(
                    text = stringResource(bookNotFoundText),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

/**
 * Book display on the reading screen
 */
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
