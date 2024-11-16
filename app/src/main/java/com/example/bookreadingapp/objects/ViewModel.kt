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
    var searchResultText by mutableStateOf("")
    // Track whether a book is selected
    var bookSelected by mutableStateOf(false)
    // Track if the book is deselected (to prevent looping navigation)
    var bookDeselected by mutableStateOf(true)

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

    fun performSearch() {
        if (searchBarInput.isNotBlank()) {
            searchResultText = "Searching for the word ${searchBarInput}"
        }
    }

    // Update book selection state (true if selected, false if deselected)
    fun updateBookSelected(isSelected: Boolean) {
        bookSelected = isSelected
    }

    // Update book deselection state (true if deselected, false if selected)
    fun updateBookDeselected(isDeselected: Boolean) {
        bookDeselected = isDeselected
    }
}
