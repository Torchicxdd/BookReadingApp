package com.example.bookreadingapp.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.room.Insert
import com.example.bookreadingapp.R

@Composable
fun ProgressMessage(progress: Int, progressInsert: Int) {
    Column(
        modifier = Modifier
            .testTag("bookshelf_screen")
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.download_progress, progress),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.insert_progress, progressInsert),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}