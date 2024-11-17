package com.example.bookreadingapp.screens

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
import androidx.compose.ui.text.style.TextAlign
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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            HomeContent(context)
        }
    }
}

@Composable
fun HomeContent(
    context: Context
) {
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
        Description(context)
        HowToUse(context)
    }
}

@Composable
fun Description(
    context: Context
) {
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
                text = context.getString(R.string.welcome_message),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            )

            // App description
            val descriptionArray = context.resources.getStringArray(R.array.app_description)
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

@Composable
fun HowToUse(
    context: Context
) {
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
                text = context.getString(R.string.welcome_message),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            )

            // "How to Use" steps
            val howToUseStepsArray = context.resources.getStringArray(R.array.how_to_use_steps)
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