package com.example.bookreadingapp.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.entities.Chapters
import com.example.bookreadingapp.ui.utils.GoToReadingButton
import com.example.bookreadingapp.ui.utils.GoToSearchButton
import com.example.bookreadingapp.ui.viewmodels.MainViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

/**
 * Table of contents page
 * Accessed when a book is clicked
 * Allows navigation to reading mode and search screens
 */
@Composable
fun ContentTable(
    book: Book?,
    mainViewModel: MainViewModel,
    navigateToSearch: () -> Unit,
    navigateToReading: (Long) -> Unit,
) {
    // Observe search results in viewmodel
    val searchChapterResults by mainViewModel.chapterViewModel.searchResults.observeAsState(listOf())
    if (book != null) {
        mainViewModel.chapterViewModel.getChaptersByBookId(book.bookID)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("content_screen")
    ) {
        // Content section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
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

@Composable
fun TableOfContentsHeader(
    @DrawableRes bookCover: Int,
    @StringRes bookTitle: Int,
    navigateToSearch: () -> Unit,
    navigateToReading: (Long) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
               .padding(top = dimensionResource(R.dimen.padding_medium))
       )
       LazyColumn (
           modifier = Modifier
               .fillMaxSize(),
           content = {
               items(chapters) { chapter ->
                   DisplayChapter(chapter = chapter, navigateToReading = navigateToReading)
               }
           }
       )
   }
}

@Composable
fun DisplayChapter(
    chapter: Chapters,
    navigateToReading: (Long) -> Unit
) {
    Text(
        text = chapter.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(R.dimen.padding_small),
                bottom = dimensionResource(R.dimen.padding_small)
            )
            .clickable(
                onClick = {
                    navigateToReading(chapter.id)
                }
            )
    )
}
