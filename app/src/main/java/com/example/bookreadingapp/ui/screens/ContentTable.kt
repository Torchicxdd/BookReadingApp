package com.example.bookreadingapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.entities.Chapters
import com.example.bookreadingapp.ui.utils.GoToReadingButton
import com.example.bookreadingapp.ui.utils.GoToSearchButton
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.viewmodels.MainViewModel

/**
 * Table of contents page
 * Accessed when a book is clicked
 * Allows navigation to reading mode and search screens
 */
@Composable
fun ContentTable(
    book: Book?,
    viewModel: AppViewModel,
    mainViewModel: MainViewModel,
    navigateToSearch: () -> Unit,
    navigateToReading: (Long) -> Unit
) {
    // Observe search results in viewmodel
    val searchChapterResults by mainViewModel.chapterViewModel.searchResults.observeAsState(listOf())
    if (book != null) {
        mainViewModel.chapterViewModel.getChaptersByBookId(book.bookID)
        // Set chapter list in view model
        viewModel.currentBookChapterList = searchChapterResults.map { chapter ->
            chapter.id
        }
    }

    TableOfContentMain(
        book = book,
        navigateToSearch = navigateToSearch,
        navigateToReading = navigateToReading,
        searchChapterResults = searchChapterResults
    )
}

/**
 * Main method for Table of Content
 */
@Composable
fun TableOfContentMain(
    book: Book?,
    navigateToSearch: () -> Unit,
    navigateToReading: (Long) -> Unit,
    searchChapterResults: List<Chapters>
) {
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("content_screen")
            .verticalScroll(verticalScrollState)
    ) {
        // Content section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Title and selected book information
            TableOfContentsHeader(
                bookCover = book!!.imageResourceId,
                bookTitle = book.title,
                navigateToSearch = navigateToSearch,
                navigateToReading = navigateToReading
            )

            DisplayChaptersList(chapters = searchChapterResults, navigateToReading = navigateToReading)
        }
    }
}

/**
 * Header information for the book and navigating to search and reading screens
 */
@Composable
fun TableOfContentsHeader(
    @DrawableRes bookCover: Int,
    @StringRes bookTitle: Int,
    navigateToSearch: () -> Unit,
    navigateToReading: (Long) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(bookCover),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.small_image))
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(bookTitle),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            GoToSearchButton(navigateToSearch)
            GoToReadingButton(chapterId = 0, navigateToReading = navigateToReading)
        }
    }
}

/**
 * Displays all the chapters
 */
@Composable
fun DisplayChaptersList(
    chapters: List<Chapters>,
    navigateToReading: (Long) -> Unit
) {
   Column {
       Text(
           text = stringResource(R.string.num_chap, chapters.size),
           fontWeight = FontWeight.Bold,
           modifier = Modifier
               .padding(
                   top = dimensionResource(R.dimen.padding_medium),
                   start = dimensionResource(R.dimen.padding_medium)
               )
       )
       for ((i, chapter) in chapters.withIndex()) {
           DisplayChapter(
               chapter = chapter,
               chapterNumber = i,
               navigateToReading = navigateToReading
           )
       }
   }
}

/**
 * Singular chapter display
 */
@Composable
fun DisplayChapter(
    chapter: Chapters,
    chapterNumber: Int,
    navigateToReading: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToReading(chapter.id) }
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.name_chap, chapterNumber, chapter.title),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
