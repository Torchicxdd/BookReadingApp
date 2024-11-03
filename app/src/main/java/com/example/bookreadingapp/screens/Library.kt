package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.bookreadingapp.R

@Composable
fun Library(context: Context) {
    Text(text = context.getString(R.string.library))

    Card() {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wood_cover),
                    contentDescription = context.getString(R.string.wood),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
                Text(
                    text = context.getString(R.string.wood),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.plumbing_cover),
                    contentDescription = context.getString(R.string.plumbing),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
                Text(
                    text = context.getString(R.string.plumbing),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_padding)))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hardware_cover),
                    contentDescription = context.getString(R.string.hardware),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
                Text(
                    text = context.getString(R.string.hardware),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.steam_cover),
                    contentDescription = context.getString(R.string.steam),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
                Text(
                    text = context.getString(R.string.steam),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_padding)))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dairy_cover),
                    contentDescription = context.getString(R.string.dairy),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
                Text(
                    text = context.getString(R.string.dairy),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mushroom_cover),
                    contentDescription = context.getString(R.string.mushroom),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
                Text(
                    text = context.getString(R.string.mushroom),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}