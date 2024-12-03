package com.example.bookreadingapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.books

class AppViewModel : ViewModel() {
    var readingMode by mutableStateOf(false)
    var selectedBook by mutableStateOf<Book?>(null)
    var searchBarInput by mutableStateOf("")
    var searchResultText by mutableStateOf("")

    // To keep track of changing chapters
    lateinit var currentBookChapterList: List<Long>

    // Track which books are downloading
    var downloadingBooks by mutableStateOf(mutableMapOf<Int, Boolean>())

    // MutableStateList to hold the books in the library
    private val _libraryBooks = mutableStateListOf<Book>()
    val libraryBooks: List<Book> = _libraryBooks

    // MutableStateList to hold the books in the bookshelf
    private val _bookshelfBooks = mutableStateListOf<Book>()
    val bookshelfBooks: List<Book> = _bookshelfBooks

    // A flag to ensure the library is only initialized once
    private var isLibraryInitialized = false

    var canNavigateBack by mutableStateOf(false)

    init {
        // Initialize the library books only once
        if (!isLibraryInitialized) {
            initializeLibrary()
            isLibraryInitialized = true
        }
    }

    // Function to initialize the library with predefined books
    // Learned about .addAll from here https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/
    private fun initializeLibrary() {
        _libraryBooks.addAll(books)
    }

    // Function to move a book from the library to the bookshelf
    // Learned about .add and .remove from here https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/
    fun moveBookToBookshelf(book: Book) {
        _libraryBooks.remove(book)
        _bookshelfBooks.add(book)
    }

    fun updateBook(book: Book) {
        selectedBook = book
    }

    fun updateSearchBarInput(newInput: String) {
        searchBarInput = newInput
    }

    fun performSearch() {
        if (searchBarInput.isNotBlank()) {
            searchResultText = "Searching for the word $searchBarInput"
        }
    }

    // Function to set a book's downloading state
    fun setBookDownloading(bookId: Int, isDownloading: Boolean) {
        downloadingBooks = downloadingBooks.toMutableMap().apply {
            this[bookId] = isDownloading
        }
    }
}