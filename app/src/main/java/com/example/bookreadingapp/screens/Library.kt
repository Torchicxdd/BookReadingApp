package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.bookreadingapp.R

@Composable
fun Library(context: Context) {
    Text(text = context.getString(R.string.library))

    Card() { //modifier = modifier
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wood_cover),
                    contentDescription = "The Mechanical Properties of Wood",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )

                Image(
                    painter = painterResource(id = R.drawable.plumbing_cover),
                    contentDescription = "Elements of Plumbing",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )

                Image(
                    painter = painterResource(id = R.drawable.hardware_cover),
                    contentDescription = "Hardware, estimating, and mill design",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.steam_cover),
                    contentDescription = "Steam, Its Generation and Use",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )

                Image(
                    painter = painterResource(id = R.drawable.dairy_cover),
                    contentDescription = "Outlines of Dairy Bacteriology, 8th edition",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )

                Image(
                    painter = painterResource(id = R.drawable.mushroom_cover),
                    contentDescription = "The Mushroom, Edible and Otherwise",
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                )
            }
        }
    }
}