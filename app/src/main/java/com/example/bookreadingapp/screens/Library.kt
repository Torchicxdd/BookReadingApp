package com.example.bookreadingapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bookreadingapp.objects.AppViewModel

@Composable
fun Library(
    viewModel: AppViewModel
) {
    Column {
        Text(text = "Library Screen")
        Text(text = viewModel.exampleState)

        Button(onClick = { viewModel.updateExampleState("This state was changed from the Library screen") }) {
            Text(text = "Change ViewModel state")
        }
    }
}