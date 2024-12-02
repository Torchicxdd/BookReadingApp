package com.example.bookreadingapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.download.FileDownload
import com.example.bookreadingapp.data.entities.Books
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

    private val _isDownloading = MutableStateFlow(false)
    val isDownloading: StateFlow<Boolean> get() = _isDownloading

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

            downloadFileWithProgress(url, file)
            extractZipWithProgress(file, directoryName, book, moveBookToBookshelf)
            _isDownloading.value = false
            updateDirectoryContents("")

<<<<<<< HEAD
            // Insert new book information into database
            val newBookID = book.insertBook(mainViewModel)
            book.insertElements(newBookID, mainViewModel)
=======
            book.insertBook(mainViewModel)
            book.insertChapters(mainViewModel)
>>>>>>> 1c01a4cd67612cfd78504272faada94486de6552
        }
    }

    private suspend fun downloadFileWithProgress(
        url: String,
        file: File
    ) {
        // Download zip file from url with progress update
        if (repository.downloadFile(url, file) { progress ->
                viewModelScope.launch {
                    _progressPercentage.emit(progress)
                }
            }
        ) {
            Log.i(TAG_DVM, "File Downloaded")
        } else {
            Log.e(TAG_DVM, "Failed to download file")
        }
    }

    private suspend fun extractZipWithProgress(
        file: File,
        directoryName: String,
        book: Book,
        moveBookToBookshelf: (Book) -> Unit
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
                moveBookToBookshelf(book)
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
}