package com.example.bookreadingapp.ui.screens

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bookreadingapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.utils.AdaptiveNavigationType

// The main screen of the app, which houses all the content and UI components.
@Composable
fun Home() {
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
            Text(text = stringResource(R.string.home), style = MaterialTheme.typography.displayLarge)
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            HomeContent()
        }
    }
}

// A composable function that holds the dynamic content for the home screen
@Composable
fun HomeContent(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
    ) {
        Description()
        HowToUse()
    }
}

// Displays a card containing the app description
@Composable
fun Description() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.welcome_message),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            )

            // App description
            val descriptionArray = stringArrayResource(R.array.app_description)
            descriptionArray.forEach { descriptionItem ->
                Text(
                    text = descriptionItem,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(bottom = dimensionResource(id = R.dimen.padding_small))
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}

// Displays a card with instructions on how to use the app
@Composable
fun HowToUse() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(R.string.welcome_message),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            )

            // "How to Use" steps
            val howToUseStepsArray = stringArrayResource(R.array.how_to_use_steps)
            howToUseStepsArray.forEachIndexed { index, step ->
                Text(
                    text = "${index + 1}. $step",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(bottom = dimensionResource(id = R.dimen.padding_small))
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
            }
        }
    }
}