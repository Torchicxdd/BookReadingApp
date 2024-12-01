package com.example.bookreadingapp.ui.utils

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book

// Composable to display a book item (cover and title)
@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isBookDownloading: Boolean
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable(
                enabled = !isBookDownloading, // Disable click if the book is downloading
                onClick = onClick
            )
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
            BookCover(book.imageResourceId)

            // Display book title underneath the cover
            BookInformation(book.title)
        }
    }
}

// Composable to display the book cover image
@Composable
fun BookCover(
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

// Composable to display the book title
@Composable
fun BookInformation(
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

@Composable
fun DisplayBookList(
    libraryBooks: List<Book>,
    onBookClick: (Book) -> Unit,
    isBookDownloading: (Book) -> Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        content = {
            items(libraryBooks) { book ->
                BookItem(
                    book = book,
                    onClick = { onBookClick(book) },
                    modifier = Modifier.testTag("book_item_${book.title}"),
                    isBookDownloading = isBookDownloading(book)
                )
                Log.d("TestTagLogging", "Found testTag: book_item_${book.title}")
            }
        }
    )
}
@Composable
fun DisplayBookListBookshelf(
    libraryBooks: List<Book>,
    onBookClick: (Book) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        content = {
            items(libraryBooks) { book ->
                BookItem(
                    book = book,
                    onClick = { onBookClick(book) },
                    modifier = Modifier.testTag("book_item_${book.title}"),
                    isBookDownloading = false
                )
                Log.d("TestTagLogging", "Found testTag: book_item_${book.title}")
            }
        }
    )
}