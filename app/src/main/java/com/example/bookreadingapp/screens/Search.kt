package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bookreadingapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.utils.AdaptiveNavigationType

@Composable
fun Search(
    context: Context,
    viewModel: AppViewModel,
    adaptiveNavigationType: AdaptiveNavigationType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = context.getString(R.string.search), style = MaterialTheme.typography.displayLarge)
            Text(text = viewModel.exampleState, style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { viewModel.updateExampleState("This state was changed from the Search screen") }) {
                Text(text = "Change ViewModel state", style = MaterialTheme.typography.labelSmall)
            }
        }
        SearchBar(viewModel, context)
    }
}

@Composable
fun SearchBar(viewModel: AppViewModel, context: Context) {
    TextField(
        value = viewModel.searchBarInput,
        onValueChange = { viewModel.updateSearchBarInput(it)},
        label = {
            Text(
                text = context.getString(R.string.search_input),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}