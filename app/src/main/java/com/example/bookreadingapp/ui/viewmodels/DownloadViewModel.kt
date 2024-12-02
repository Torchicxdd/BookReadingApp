package com.example.bookreadingapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.download.FileDownload
import com.example.bookreadingapp.data.entities.Books
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

private const val TAG_DVM = "DownloadViewModel"
class DownloadViewModel(private val repository: FileDownload) : ViewModel() {
    private val _directoryContents = MutableLiveData<List<String>>()
    val directoryContents: LiveData<List<String>> = _directoryContents

    private val _progressPercentage = MutableStateFlow(0)
    val progressPercentage: StateFlow<Int> get() = _progressPercentage

    private val _isDownloading = MutableStateFlow(false)
    val isDownloading: StateFlow<Boolean> get() = _isDownloading

    // Function to set up file download and data insertion
    // Returns the absolute path of the downloaded and extracted html file
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

            book.insertBook(mainViewModel)
            book.insertChapters(mainViewModel)
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

    suspend fun confirmDeletion(directoryName: String) {
        repository.deleteDirectoryContents(directoryName)
        updateDirectoryContents(directoryName)
        Log.i(TAG_DVM, "$directoryName content deleted")
    }

}