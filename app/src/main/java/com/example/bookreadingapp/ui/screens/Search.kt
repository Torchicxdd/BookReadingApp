package com.example.bookreadingapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.entities.Paragraphs
import com.example.bookreadingapp.ui.theme.md_theme_dark_onSurface
import com.example.bookreadingapp.ui.theme.md_theme_dark_surface
import com.example.bookreadingapp.ui.theme.md_theme_light_onSurface
import com.example.bookreadingapp.ui.theme.md_theme_light_surface
import com.example.bookreadingapp.ui.viewmodels.MainViewModel
import androidx.compose.runtime.*

/**
 * Search screen to use search function
 */
@Composable
fun Search(
    book: Book?,
    searchBarInput: String,
    updateSearchBar: (String) -> Unit,
    performSearch: () -> Unit,
    searchResult: String,
    navigateToReading: (Long) -> Unit,
    mainViewModel: MainViewModel
) {
    var searchPerformed by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("search_screen")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.search), style = MaterialTheme.typography.displayLarge)
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))
        SearchBar(
            book = book,
            searchBarInput = searchBarInput,
            updateSearchBar = updateSearchBar,
            performSearch = {
                performSearch()
                searchPerformed = true
            },
            searchResult = searchResult,
            mainViewModel = mainViewModel,
            searchPerformed = searchPerformed,
            navigateToReading = navigateToReading
        )
    }
}

/**
 * Search bar for user to type in search queries
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    book: Book?,
    searchBarInput: String,
    updateSearchBar: (String) -> Unit,
    performSearch: () -> Unit,
    searchResult: String,
    mainViewModel: MainViewModel,
    searchPerformed: Boolean,
    navigateToReading: (Long) -> Unit
) {
    // Observe search results in viewmodel
    val searchParagraphResults by mainViewModel.paragraphViewModel.allParagraphs.observeAsState(listOf())
    val paragraphViewModel = mainViewModel.paragraphViewModel
    if (book != null) {
        paragraphViewModel.findParagraphByBookId(book.bookID)
    }

    // Only calculates occurrences if the search has been performed
    val occurrences = if (searchPerformed) {
        remember(searchBarInput) {
            findOccurrences(searchBarInput, searchParagraphResults)
        }
    } else {
        emptyList()
    }

    // Determine if dark theme is active
    val darkTheme = isSystemInDarkTheme()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(
                id = R.string.searching_in_book,
                stringResource(book!!.title)
            )
        )
        OutlinedTextField(
            value = searchBarInput,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (darkTheme) md_theme_dark_surface else md_theme_light_surface,
                focusedBorderColor = if (darkTheme) md_theme_dark_onSurface else md_theme_light_onSurface,
                containerColor = if (darkTheme) md_theme_dark_surface else md_theme_light_surface
            ),
            onValueChange = { updateSearchBar(it) },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_input),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    performSearch()
                }
            )
        )
        DisplaySearchResults(
            searchPerformed = searchPerformed,
            occurrences = occurrences,
            searchBarInput = searchBarInput,
            navigateToReading = navigateToReading
        )
    }

}

fun findOccurrences(searchTerm: String, paragraphs: List<Paragraphs>): List<Pair<Long, Long>> {
    val occurrences = mutableListOf<Pair<Long, Long>>()
    paragraphs.forEachIndexed { index, paragraph ->
        val matches = Regex("(?i)$searchTerm").findAll(paragraph.text).toList()
        if (matches.isNotEmpty()) {
            occurrences.add(Pair(paragraph.id, paragraph.chapterId))
        }
    }
    return occurrences
}

@Composable
fun DisplaySearchResults(
    searchPerformed: Boolean,
    occurrences:  List<Pair<Long, Long>>,
    searchBarInput: String,
    navigateToReading: (Long) -> Unit,
){
    // Only display search results if search has been performed
    if (searchPerformed) {
        if (occurrences.isNotEmpty() && searchBarInput != "") {
            Text(
                text = "${occurrences.size} occurences of ${searchBarInput} found ",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(R.dimen.padding_small))
            ) {
                items(occurrences.toList()) { (paragraphId, chapterId) ->
                    Text(
                        text = "${searchBarInput} found in Chapter $chapterId, Paragraph $paragraphId",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_small))
                            .clickable(onClick = {
                                navigateToReading(chapterId)
                            })
                    )
                }
            }
        }
    }
}


