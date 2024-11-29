package com.example.bookreadingapp.ui.screens

import android.content.Context
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.objects.Routes
import com.example.bookreadingapp.ui.utils.AdaptiveNavigationType

// Main composable function for the bookshelf screen
@Composable
fun Bookshelf(
    context: Context,
    bookshelfBooks: List<Book>,
    updateBook: (Book) -> Unit,
    navigateToTableOfContents: () -> Unit
) {
    // Check if the bookshelf has any books
    if (bookshelfBooks.isEmpty()) {
        NoBooksAvailableMessage(context = context)
    } else {
        BooksAvailable(
            books = bookshelfBooks,
            onBookClick = { book ->
                updateBook(book)
                navigateToTableOfContents()
            }
        )
    }
}

// Composable function to display a message when no books are available
@Composable
fun NoBooksAvailableMessage(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("no_books_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = context.getString(R.string.no_books_available),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.testTag("empty_bookshelf_text")
        )
    }
}

// Composable function to display the available books in a grid layout
@Composable
fun BooksAvailable(
    books: List<Book>,
    onBookClick: (Book) -> Unit
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