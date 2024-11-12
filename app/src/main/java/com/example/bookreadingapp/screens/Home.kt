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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.utils.AdaptiveNavigationType

@Composable
fun Home(
    context: Context,
    viewModel: AppViewModel,
    adaptiveNavigationType: AdaptiveNavigationType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = context.getString(R.string.home), style = MaterialTheme.typography.displayLarge)
        }
    }
}