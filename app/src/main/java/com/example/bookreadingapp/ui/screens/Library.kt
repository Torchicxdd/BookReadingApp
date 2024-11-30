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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.bookreadingapp.ui.theme.Shapes
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel

// Composable function that represents the main screen of the Library
@Composable
fun Library(
    libraryBooks: List<Book>,
    setupDownload: (String, String) -> String,
    downloadViewModel: DownloadViewModel,
    updateCurrentDownloadingBook: (Book?) -> Unit,
    onDownloadCompleteLibrary: () -> Unit,
    onDownloadCompleteBookshelf: () -> Unit,
) {
    val urlList = stringArrayResource(R.array.download)
    val progressPercentage by downloadViewModel.progressPercentage.collectAsState()
    val isDownloading by downloadViewModel.isDownloading.collectAsState()

    // Check if download is complete, if so, move book to bookshelf
    LaunchedEffect(isDownloading) {
        if (!isDownloading) {
            // Remove book from library when download completes
            onDownloadCompleteLibrary()
            // Adds book to bookshelf when download completes
            onDownloadCompleteBookshelf()
            // Clear currentlyDownload after removing it from the library
            updateCurrentDownloadingBook(null)
        }
    }

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
            if (isDownloading) {
                ProgressMessage(
                    progress = progressPercentage
                )
            }
        }
        // Check if the library has books
        if (libraryBooks.isEmpty()) {
            NoBooksToDownloadMessage()
        } else {
            // LazyVerticalGrid for the book items
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    items(libraryBooks) { book ->
                        BookItem(book = book, onClick = {
                            // Starts downloading only if this book is not already being downloaded
                            updateCurrentDownloadingBook(book)
                            book.htmlFilePath = downloadBookFiles(setupDownload, urlList[book.arrayIndex])
                        },
                        modifier = Modifier.testTag("book_item_${book.title}"))
                        Log.d("TestTagLogging", "Found testTag: book_item_${book.title}")
                    }
                }
            )
        }
    }
}
// Function to download book files from the provided URL
private fun downloadBookFiles(
    setupDownload: (String, String) -> String,
    url: String
) : String {
    return setupDownload(
        url,
        "${url.substringAfterLast("/").replace(".zip", "")}-dir"
    )
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

// Composable to display a book item (cover and title)
@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
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