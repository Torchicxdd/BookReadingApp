package com.example.bookreadingapp.screens

import android.content.Context
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.books
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.objects.Routes
import com.example.bookreadingapp.utils.AdaptiveNavigationType

@Composable
fun Bookshelf(
    context: Context,
    viewModel: AppViewModel,
    navController: NavController,
    adaptiveNavigationType: AdaptiveNavigationType
) {
    // Check if the bookshelf has any books
    if (viewModel.bookshelfBooks.isEmpty()) {
        NoBooksAvailableMessage(context = context)
    } else {
        BooksAvailable(
            books = viewModel.bookshelfBooks,
            navController = navController,
            onBookClick = { book ->
                viewModel.updateBookTitle(book.title)
                navController.navigate(Routes.ContentTable.route) {
                    popUpTo(Routes.Library.route) { inclusive = true }
                }
            }
        )
    }
}

@Composable
fun NoBooksAvailableMessage(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = context.getString(R.string.no_books_available),  // Use the string resource
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun BooksAvailable(
    books: List<Book>,
    navController: NavController,
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