package com.example.bookreadingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.ui.utils.GoToReadingButton
import com.example.bookreadingapp.ui.utils.GoToSearchButton
import com.example.bookreadingapp.ui.viewmodels.MainViewModel

/**
 * Table of contents page
 * Accessed when a book is clicked
 * Allows navigation to reading mode and search screens
 */
@Composable
fun ContentTable(
    book: Book?,
    mainViewModel: MainViewModel,
    navigateToSearch: () -> Unit,
    navigateToReading: () -> Unit,
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
            Text(text = stringResource(R.string.content), style = MaterialTheme.typography.displayLarge)
            Text(
                text = stringResource(
                    R.string.book_chosen,
                    stringResource(book!!.title)
                )
            )

            // Navigation buttons for Bookshelf, Search, and Reading screens
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                GoToSearchButton(navigateToSearch)
                GoToReadingButton(navigateToReading)
            }
        }
    }
}