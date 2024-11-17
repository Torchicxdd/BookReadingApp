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
    // If there are no books in the bookshelf
    if (viewModel.bookshelfBooks.isEmpty()) {
        // Display a message indicating no books are available
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = context.getString(R.string.no_books_available),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        // If there are books, display them in a grid
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag("bookshelf_screen")
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = context.getString(R.string.bookshelf), style = MaterialTheme.typography.displayLarge)
            }

            // LazyVerticalGrid for the book items
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    items(viewModel.bookshelfBooks) { book ->
                        BookshelfItem(book = book, onClick = {
                            viewModel.updateBookTitle(book.title)
                            navController.navigate(Routes.ContentTable.route) {
                                popUpTo(Routes.Library.route) { inclusive = true }
                            }
                        }, modifier = Modifier.testTag("book_item_${book.title}"))
                        Log.d("TestTagLogging", "Found testTag: book_item_${book.title}")
                    }
                }
            )
        }
    }
}

@Composable
fun BookshelfItem(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            // Display book cover
            BookshelfCover(book.imageResourceId)

            // Display book title underneath the cover
            BookshelfInformation(book.title)
        }
    }
}

@Composable
fun BookshelfCover(
    @DrawableRes bookCover: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.padding_medium)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_size)),
            painter = painterResource(bookCover),
            contentDescription = null
        )
    }
}

@Composable
fun BookshelfInformation(
    @StringRes bookTitle: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(bookTitle),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium)
                )
        )
    }
}
