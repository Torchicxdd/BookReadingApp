package com.example.bookreadingapp.screens

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bookreadingapp.R

@Composable
fun Library(context: Context) {
    Text(text = context.getString(R.string.library))
}