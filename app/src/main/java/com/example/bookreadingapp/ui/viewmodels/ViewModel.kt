package com.example.bookreadingapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
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

    fun createPages(
        paragraphs: List<String>,
        textMeasurer: TextMeasurer,
        maxHeightPx: Float,
        width: Dp,
        fontSize: TextUnit,
        localDensity: Density
    ): List<List<String>> {
        val pages = mutableListOf<List<String>>()
        val currentPage = mutableListOf<String>()
        var currentHeight = 0f

        paragraphs.forEach { paragraph ->
            // Find the height of the paragraph in Float
            val paragraphHeight = textMeasurer.measure(
                text = paragraph,
                style = TextStyle(fontSize = fontSize),
                constraints = Constraints(maxWidth = with(localDensity) { width.toPx().toInt() })
            ).size.height.toFloat()

            // If the new paragraph and current page height is larger than the height
            if (currentHeight + paragraphHeight > maxHeightPx && currentPage.isNotEmpty()) {
                pages.add(currentPage.toList())
                currentPage.clear()
                currentHeight = 0f
            }

            // If not bigger, add paragraph to page
            currentPage.add(paragraph)
            currentHeight += paragraphHeight
        }

        // Add paragraph if returned not bigger but still with content
        if (currentPage.isNotEmpty()) {
            pages.add(currentPage)
        }

        return pages
    }
}