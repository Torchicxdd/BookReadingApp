package com.example.bookreadingapp.objects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    var exampleState by mutableStateOf("This is the state before being changed")
    var readingMode by mutableStateOf(false)

    fun updateExampleState(newText: String) {
        exampleState = newText
    }

    fun updateReadingMode() {
        readingMode = !readingMode
    }
}
