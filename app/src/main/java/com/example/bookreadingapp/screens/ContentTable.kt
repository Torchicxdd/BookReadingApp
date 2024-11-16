package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.material3.Text
import com.example.bookreadingapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.objects.Routes
import com.example.bookreadingapp.utils.AdaptiveNavigationType

@Composable
fun ContentTable(
    context: Context,
    viewModel: AppViewModel,
    navController: NavController,
    adaptiveNavigationType: AdaptiveNavigationType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("content_screen")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(text = context.getString(R.string.content), style = MaterialTheme.typography.displayLarge)
            Text(text = "Book Chosen: ${stringResource(viewModel.selectedBookTitleResId)}")
            Button(
                onClick = {
                    navController.navigate(Routes.Reading.route) {
                    popUpTo(Routes.ContentTable.route) { inclusive = true }
                } }
            ){
                Text(text = "Navigate to Reading Screen", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}