package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bookreadingapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavController
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.ui.BackToBookshelfButton
import com.example.bookreadingapp.ui.GoToReadingButton
import com.example.bookreadingapp.ui.GoToTableContentButton
import com.example.bookreadingapp.ui.theme.md_theme_dark_onSurface
import com.example.bookreadingapp.ui.theme.md_theme_dark_surface
import com.example.bookreadingapp.ui.theme.md_theme_light_onSurface
import com.example.bookreadingapp.ui.theme.md_theme_light_surface
import com.example.bookreadingapp.utils.AdaptiveNavigationType

@Composable
fun Search(
    context: Context,
    viewModel: AppViewModel,
    navController: NavController,
    adaptiveNavigationType: AdaptiveNavigationType
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
            Text(text = viewModel.exampleState, style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("search_viewmodel_text"))

            // Navigation buttons for Bookshelf, Table of Content, and Reading screens
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                BackToBookshelfButton(navController = navController)
                GoToTableContentButton(navController = navController)
                GoToReadingButton(navController = navController)
            }
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))
        SearchBar(viewModel, context)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: AppViewModel, context: Context) {
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
                stringResource(viewModel.selectedBookTitleResId)
            )
        )
        OutlinedTextField(
            value = viewModel.searchBarInput,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors =TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor =  if (darkTheme) md_theme_dark_surface else md_theme_light_surface,
                focusedBorderColor =  if (darkTheme) md_theme_dark_onSurface else md_theme_light_onSurface,
                containerColor = if (darkTheme) md_theme_dark_surface else md_theme_light_surface
            ),
            onValueChange = { viewModel.updateSearchBarInput(it) },
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
                    viewModel.performSearch()
                }
            )
        )
        // Display the search result if it's not empty
        if (viewModel.searchResultText.isNotEmpty()) {
            DisplayFoundWord(viewModel.searchResultText)
        }
    }
}

@Composable
fun DisplayFoundWord(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_small))
    )
}