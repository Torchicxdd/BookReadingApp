package com.example.bookreadingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.entities.Books
import com.example.bookreadingapp.ui.utils.DisplayBookList
import com.example.bookreadingapp.ui.utils.ProgressMessage

// Composable function that represents the main screen of the Library
@Composable
fun Library(
    libraryBooks: List<Book>,
    moveBookToBookshelf: (Book) -> Unit,
    progressPercentage: State<Int>,
    isDownloading: State<Boolean>,
    setupDownload: (String, String, Book, (Book) -> Unit ) -> Unit,
    insertBook: (Books) -> Unit
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
                    insertBook(Books(
                        "Test book", "AUTHOR_HERE", book.imageResourceId.toString()
                    ))
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
