package com.example.bookreadingapp.ui.screens

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bookreadingapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.bookreadingapp.ui.theme.md_theme_dark_onSurface
import com.example.bookreadingapp.ui.theme.md_theme_dark_surface
import com.example.bookreadingapp.ui.theme.md_theme_light_onSurface
import com.example.bookreadingapp.ui.theme.md_theme_light_surface

/**
 * Search screen to use search function
 */
@Composable
fun Search(
    context: Context,
    @StringRes selectedBookTitle: Int,
    searchBarInput: String,
    updateSearchBar: (String) -> Unit,
    performSearch: () -> Unit,
    searchResult: String
) {
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
            Text(text = context.getString(R.string.search), style = MaterialTheme.typography.displayLarge)
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))
        SearchBar(
            context = context,
            selectedBookTitle = selectedBookTitle,
            searchBarInput = searchBarInput,
            updateSearchBar = updateSearchBar,
            performSearch = performSearch,
            searchResult = searchResult
        )
    }
}

/**
 * Search bar for user to type in search queries
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    context: Context,
    @StringRes selectedBookTitle: Int,
    searchBarInput: String,
    updateSearchBar: (String) -> Unit,
    performSearch: () -> Unit,
    searchResult: String
) {
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
                stringResource(selectedBookTitle)
            )
        )
        OutlinedTextField(
            value = searchBarInput,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors =TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor =  if (darkTheme) md_theme_dark_surface else md_theme_light_surface,
                focusedBorderColor =  if (darkTheme) md_theme_dark_onSurface else md_theme_light_onSurface,
                containerColor = if (darkTheme) md_theme_dark_surface else md_theme_light_surface
            ),
            onValueChange = { updateSearchBar(it) },
            placeholder = {
                Text(
                    text = context.getString(R.string.search_input),
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
        // Display the search result if it's not empty
        if (searchResult.isNotEmpty()) {
            DisplayFoundWord(searchResult)
        }
    }
}

/**
 * Display for finding a word in the text
 */
@Composable
fun DisplayFoundWord(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_small))
    )
}