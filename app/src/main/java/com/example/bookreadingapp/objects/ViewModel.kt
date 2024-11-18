package com.example.bookreadingapp.objects

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.books
import com.example.bookreadingapp.downloads.FileDownload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class AppViewModel : ViewModel() {
    var exampleState by mutableStateOf("This is the state before being changed")
    var readingMode by mutableStateOf(false)
    var selectedBookTitleResId by mutableIntStateOf(0)
    var searchBarInput by mutableStateOf("")
    var searchResultText by mutableStateOf("")

    // MutableStateList to hold the books in the library
    private val _libraryBooks = mutableStateListOf<Book>()
    val libraryBooks: List<Book> = _libraryBooks

    // MutableStateList to hold the books in the bookshelf
    private val _bookshelfBooks = mutableStateListOf<Book>()
    val bookshelfBooks: List<Book> = _bookshelfBooks

    // A flag to ensure the library is only initialized once
    private var isLibraryInitialized = false

    init {
        // Initialize the library books only once
        if (!isLibraryInitialized) {
            initializeLibrary()
            isLibraryInitialized = true
        }
    }

    // Function to initialize the library with predefined books
    // Learned about .addAll from here https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/
    fun initializeLibrary() {
        _libraryBooks.addAll(books)
    }

    // Function to move a book from the library to the bookshelf
    // Learned about .add and .remove from here https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/
    fun moveBookToBookshelf(book: Book) {
        _libraryBooks.remove(book)
        _bookshelfBooks.add(book)
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
            searchResultText = "Searching for the word $searchBarInput"
        }
    }
}

private const val TAG_DVM = "DownloadViewModel"
class DownloadViewModel(private val repository: FileDownload) : ViewModel() {
    private val _directoryContents = MutableLiveData<List<String>>()
    val directoryContents: LiveData<List<String>> = _directoryContents

    // Function to set up file download
    fun setupDownload(url: String, directoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val fileName = url.substringAfterLast("/")
            val file = repository.createFile(directoryName, fileName)

            // Download zip file from url
            if (repository.downloadFile(url, file)) Log.i(TAG_DVM, "File Downloaded")
                else Log.e(TAG_DVM, "Failed to download file")

            // Extract zip file after downloading
            try {
                repository.unzipFile(file, directoryName)
            } catch(e: IOException) {
                e.printStackTrace()
                e.message?.let { Log.e(TAG_DVM, it) }
            }

            updateDirectoryContents(directoryName)
        }
    }

    private fun updateDirectoryContents(directoryName: String) {
        val contents = repository.listDirectoryContents(directoryName)
        _directoryContents.postValue(contents)
    }

    fun confirmDeletion(directoryName: String) {
        repository.deleteDirectoryContents(directoryName)
        updateDirectoryContents(directoryName)
        Log.i(TAG_DVM, "${directoryName} content deleted")
    }
}
