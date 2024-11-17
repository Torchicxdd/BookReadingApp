package com.example.bookreadingapp.objects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.books

class AppViewModel : ViewModel() {
    var exampleState by mutableStateOf("This is the state before being changed")
    var readingMode by mutableStateOf(false)
    var selectedBookTitleResId by mutableStateOf(0)
    var searchBarInput by mutableStateOf("")
    var searchResultText by mutableStateOf("")

    // Initial state: all books are in the library
    var libraryBooks = mutableStateListOf<Book>()
        private set

    var bookshelfBooks = mutableStateListOf<Book>()
        private set

    // Adding all books to the libraryBooks list manually in a function
    fun initializeLibrary() {
        for (book in books) {
            libraryBooks.add(book)
        }
    }

    // Moving a book from library to bookshelf
    fun moveBookToBookshelf(book: Book) {
        // Removing the book from the library and add it to the bookshelf
        libraryBooks.remove(book)
        bookshelfBooks.add(book)
    }

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
}
