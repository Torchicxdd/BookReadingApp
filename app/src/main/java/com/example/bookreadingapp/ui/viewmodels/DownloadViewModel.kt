package com.example.bookreadingapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.download.FileDownload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

private const val TAG_DVM = "DownloadViewModel"
class DownloadViewModel(private val repository: FileDownload) : ViewModel() {
    private val _directoryContents = MutableLiveData<List<String>>()

    private val _progressPercentage = MutableStateFlow(0)
    val progressPercentage: StateFlow<Int> get() = _progressPercentage

    private val _progressInsertPercentage = MutableStateFlow(0)
    val progressInsertPercentage: StateFlow<Int> get() = _progressInsertPercentage

    private val _isDownloading = MutableStateFlow(false)
    val isDownloading: StateFlow<Boolean> get() = _isDownloading

    private val _isInserting = MutableStateFlow(false)
    val isInserting: StateFlow<Boolean> get() = _isInserting

    // Function to set up file download and data insertion
    fun setupDownload(
        url: String,
        directoryName: String,
        book: Book,
        mainViewModel: MainViewModel,
        moveBookToBookshelf: (Book) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val fileName = url.substringAfterLast("/")

            val file = repository.createFile(directoryName, fileName)

            // Initialize progress
            _progressPercentage.emit(0)
            _isDownloading.emit(true)
            _progressInsertPercentage.emit(0)

            // Start downloading the file
            downloadFileWithProgress(url, file)
            // Start extracting the zip file
            extractZipWithProgress(file, directoryName, book)
            _isDownloading.emit(false)
            updateDirectoryContents("")
            _isInserting.emit(true)

            // Insert book information into the database
            val newBookID = book.insertBook(mainViewModel)
            book.insertElements(newBookID, mainViewModel, _progressInsertPercentage)
            _isInserting.emit(false)

            // Check if both download and insertion are complete
            checkCompletion(book, moveBookToBookshelf)
        }
    }

    private suspend fun downloadFileWithProgress(
        url: String,
        file: File
    ) {
        // Download zip file from URL with progress update
        if (repository.downloadFile(url, file) { progress ->
                viewModelScope.launch {
                    _progressPercentage.emit(progress)
                }
            }) {
            Log.i(TAG_DVM, "File Downloaded")
        } else {
            Log.e(TAG_DVM, "Failed to download file")
        }
    }

    private suspend fun extractZipWithProgress(
        file: File,
        directoryName: String,
        book: Book
    ) {
        // Extract zip file after downloading
        try {
            val downloadedFilePath = repository.unzipFile(file, directoryName) { progress ->
                viewModelScope.launch {
                    _progressPercentage.emit(progress)
                }
            }
            if (downloadedFilePath.isNotEmpty()) {
                book.htmlFilePath = downloadedFilePath
            } else {
                Log.e(TAG_DVM, "Download file path is empty, book was not moved to bookshelf")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            e.message?.let { Log.e(TAG_DVM, "Failed to extract file! Error: $it") }
        }
    }

    private suspend fun updateDirectoryContents(directoryName: String) {
        val contents = repository.listDirectoryContents(directoryName)
        _directoryContents.postValue(contents)
    }

    // Check if both download and insert processes are complete
    private fun checkCompletion(book: Book, moveBookToBookshelf: (Book) -> Unit) {
        if (_progressPercentage.value == 100 && _progressInsertPercentage.value == 100) {
            // Move the book to bookshelf when both download and insert are complete
            moveBookToBookshelf(book)
        }
    }
}
