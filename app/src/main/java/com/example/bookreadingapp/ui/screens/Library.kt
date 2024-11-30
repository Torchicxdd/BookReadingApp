package com.example.bookreadingapp.ui.screens

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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel
import com.example.bookreadingapp.ui.utils.AdaptiveNavigationType

// Composable function that represents the main screen of the Library
@Composable
fun Library(
    context: Context,
    viewModel: AppViewModel,
    navController: NavController,
    adaptiveNavigationType: AdaptiveNavigationType,
    downloadViewModel: DownloadViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val urlList = stringArrayResource(R.array.download)

    // Callback to move the book once download and unzip are finished
    downloadViewModel.onDownloadComplete = {
        // After download and unzip complete, moves the book to the bookshelf
        val book = viewModel.currentDownloadingBook
        if (book != null) {
            viewModel.moveBookToBookshelf(book)
            // Resets the download state after moving to bookshelf
            viewModel.currentDownloadingBook = null
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
            Text(text = context.getString(R.string.library), style = MaterialTheme.typography.displayLarge)
            // Display progress message if download or unzip is ongoing
            if (downloadViewModel.isDownloading) {
                ProgressMessage(
                    progress = downloadViewModel.totalProgress.value,
                    context = context
                )
            }
        }

        // Check if the library has books
        if (viewModel.libraryBooks.isEmpty()) {
            NoBooksToDownloadMessage(context)
        } else {
            // LazyVerticalGrid for the book items
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    items(viewModel.libraryBooks) { book ->
                        BookItem(book = book, onClick = {
                            // Starts downloading only if this book is not already being downloaded
                            if (viewModel.currentDownloadingBook == null) {
                                viewModel.currentDownloadingBook = book
                                downloadViewModel.collectProgressUpdates()
                                book.htmlFilePath = downloadBookFiles(downloadViewModel, urlList[book.arrayIndex])
                            }
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
    downloadViewModel: DownloadViewModel,
    url: String
) : String {
    return downloadViewModel.setupDownload(
        url,
        "${url.substringAfterLast("/").replace(".zip", "")}-dir"
    )
}

// Composable to display a message when there are no books to download
@Composable
fun NoBooksToDownloadMessage(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = context.getString(R.string.no_books_to_download),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Composable to display a book item (cover and title)
@Composable
fun BookItem(
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
fun ProgressMessage(progress: Int, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = context.getString(R.string.download_progress, progress),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}