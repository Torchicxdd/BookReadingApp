package com.example.bookreadingapp.objects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    var exampleState by mutableStateOf("This is the state before being changed")
    var readingMode by mutableStateOf(false)
    var selectedBookTitleResId by mutableStateOf(0)
    var searchBarInput by mutableStateOf("")

    fun updateExampleState(newText: String) {
        exampleState = newText
    }

    fun updateReadingMode() {
        readingMode = !readingMode
    }

    fun updateBookTitle(resId: Int) {
        selectedBookTitleResId = resId
    }

    fun updateSearchBarInput(newInput: String) {
        searchBarInput = newInput
    }
}
