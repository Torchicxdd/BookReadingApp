package com.example.bookreadingapp.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.ui.utils.DisplayBookList

// Composable function that represents the main screen of the Library
@Composable
fun Library(
    libraryBooks: List<Book>,
    moveBookToBookshelf: (Book) -> Unit,
    progressPercentage: State<Int>,
    isDownloading: State<Boolean>,
    setupDownload: (String, String, Book, (Book) -> Unit ) -> Unit
) {
    val urlList = stringArrayResource(R.array.download)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("library_screen")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.library), style = MaterialTheme.typography.displayLarge)

            // Display progress message if download or unzip is ongoing
            if (isDownloading.value) {
                ProgressMessage(
                    progress = progressPercentage.value
                )
            }
        }
        // Check if the library has books
        if (libraryBooks.isEmpty()) {
            NoBooksToDownloadMessage()
        } else {
            // LazyVerticalGrid for the book items
            DisplayBookList(
                libraryBooks = libraryBooks,
                onBookClick = { book ->
                    // Starts downloading only if this book is not already being downloaded
                    //viewModel.updateCurrentDownloadingBook(book)
                    val url = urlList[book.arrayIndex]
                    setupDownload(
                        url,
                        "${url.substringAfterLast("/").replace(".zip", "")}-dir",
                        book,
                        moveBookToBookshelf
                    )
                }
            )
        }
    }
}

// Composable to display a message when there are no books to download
@Composable
fun NoBooksToDownloadMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_books_to_download),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ProgressMessage(progress: Int) {
    Column(
        modifier = Modifier
            .testTag("bookshelf_screen")
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.download_progress, progress),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}