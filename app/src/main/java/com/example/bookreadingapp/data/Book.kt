package com.example.bookreadingapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.bookreadingapp.R

class Book (
    @DrawableRes val imageResourceId: Int,
    @StringRes val title: Int,
)

val books = listOf(
    Book(R.drawable.wood_cover, R.string.wood),
    Book(R.drawable.plumbing_cover, R.string.plumbing),
    Book(R.drawable.hardware_cover, R.string.hardware),
    Book(R.drawable.steam_cover, R.string.steam),
    Book(R.drawable.dairy_cover, R.string.dairy),
    Book(R.drawable.mushroom_cover, R.string.mushroom)
)