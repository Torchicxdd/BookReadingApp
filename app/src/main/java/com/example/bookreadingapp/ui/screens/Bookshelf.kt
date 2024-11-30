package com.example.bookreadingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel

// Main composable function for the bookshelf screen
@Composable
fun Bookshelf(
    bookshelfBooks: List<Book>,
    updateBook: (Book) -> Unit,
    navigateToTableOfContents: () -> Unit,
    downloadViewModel: DownloadViewModel,
    updateCurrentDownloadingBook: (Book?) -> Unit,
    onDownloadCompleteLibrary: () -> Unit,
    onDownloadCompleteBookshelf: () -> Unit
) {
    val progressPercentage by downloadViewModel.progressPercentage.collectAsState()
    val isDownloading by downloadViewModel.isDownloading.collectAsState()
    var books: List<Book> = bookshelfBooks
    // Check if download is complete, if so, move book to bookshelf
    LaunchedEffect(isDownloading) {
        if (!isDownloading) {
            // Trigger move to bookshelf when download completes
            onDownloadCompleteLibrary()
            // Trigger move to bookshelf when download completes
            onDownloadCompleteBookshelf()
            // Clear currentlyDownload after removing it from the library
            updateCurrentDownloadingBook(null)
        }
    }
    BooksAvailable(
        books = books,
        onBookClick = { book ->
            updateBook(book)
            navigateToTableOfContents()
        },
        isDownloading,
        progressPercentage
    )
}

// Composable function to display a message when no books are available
@Composable
fun NoBooksAvailableMessage(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("no_books_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_books_available),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.testTag("empty_bookshelf_text")
        )
    }
}

// Composable function to display the available books in a grid layout
@Composable
fun BooksAvailable(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    isDownloading: Boolean,
    progressPercentage: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("bookshelf_screen")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.bookshelf), style = MaterialTheme.typography.displayLarge)
            // Check if the bookshelf has any books
            if (books.isEmpty() && !isDownloading) {
                NoBooksAvailableMessage()
            }

            // Display progress message if download or unzip is ongoing
            if (isDownloading) {
                ProgressMessage(
                    progress = progressPercentage
                )
            }
        }
        // LazyVerticalGrid to display books in a grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(books) { book ->
                    BookItem(
                        book = book,
                        onClick = {
                            onBookClick(book)
                        },
                        modifier = Modifier.testTag("book_item_${book.title}")
                    )
                }
            }
        )
    }
}