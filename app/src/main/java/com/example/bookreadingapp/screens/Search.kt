package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bookreadingapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import com.example.bookreadingapp.objects.AppViewModel

@Composable
fun Search(context: Context, viewModel: AppViewModel) {
    Column {
        Text(text = context.getString(R.string.search))
        Text(text = viewModel.exampleState)

        Button(onClick = { viewModel.updateExampleState("This state was changed from the Search screen") }) {
            Text(text = "Change ViewModel state")
        }
    }
}