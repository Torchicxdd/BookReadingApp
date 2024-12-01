package com.example.bookreadingapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.bookreadingapp.ui.extensions.detectedTapWithoutSwipe
import com.example.bookreadingapp.ui.utils.BookCover


/**
 * Reading screen containing the book to read
 */
@Composable
fun Reading(
    book: Book?,
    readingMode: Boolean,
    toggleReadingMode: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .detectedTapWithoutSwipe(
                onTap = toggleReadingMode
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .testTag("reading_screen")
        ) {
            Text(
                text = stringResource(R.string.reading),
                style = MaterialTheme.typography.displayLarge
            )

            BookDisplay(
                imageResourceId = book!!.imageResourceId,
                titleResourceId = book.title
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        // Display the chapter navigation only if not in reading mode
        if (!readingMode) {
            ChapterNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.padding_small))
            )
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

/**
 * The button used to navigate through a chapter
 */
@Composable
fun ChapterNavigation(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ){
        Button(
            onClick = {/** TO DO: Go back to previous chapter (page?) */},
        ){
            Text(
                text = stringResource(R.string.prev_chap),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Button(
            onClick = {/** TO DO: Go back to next chapter (page?) */},
        ){
            Text(
                text = stringResource(R.string.next_chap),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}