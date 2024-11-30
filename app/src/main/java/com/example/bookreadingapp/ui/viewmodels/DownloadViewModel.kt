package com.example.bookreadingapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreadingapp.data.download.FileDownload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.io.IOException

private const val TAG_DVM = "DownloadViewModel"
class DownloadViewModel(private val repository: FileDownload) : ViewModel() {
    private val _directoryContents = MutableLiveData<List<String>>()
    val directoryContents: LiveData<List<String>> = _directoryContents
    // State to hold the total progress (0-100%)
    var totalProgress by mutableStateOf(0)
    // Channel to send progress updates
    private val progressChannel = Channel<Int>()
    // State to track if a book is being downloaded
    var isDownloading by mutableStateOf(false)
    // Add a callback for completion
    var onDownloadComplete: (() -> Unit)? = null

    // Function to set up file download
    // Returns the absolute path of the downloaded and extracted html file
    fun setupDownload(url: String, directoryName: String) : String {
        var downloadedFilePath = ""
        // Set the download state to true
        isDownloading = true
        viewModelScope.launch(Dispatchers.IO) {
            val fileName = url.substringAfterLast("/")
            val file = repository.createFile(directoryName, fileName)

            // Start the download process and update progress
            launch(Dispatchers.Main)  { performDownload(url, file) }

            // Start the unzip process and update progress
            launch(Dispatchers.Main)  { performUnzip(file, directoryName) }

            // Download zip file from url
            if (repository.downloadFile(url, file)) Log.i(TAG_DVM, "File Downloaded")
            else Log.e(TAG_DVM, "Failed to download file")

            // Extract zip file after downloading
            try {
                downloadedFilePath = repository.unzipFile(file, directoryName)
            } catch(e: IOException) {
                e.printStackTrace()
                e.message?.let { Log.e(TAG_DVM, it) }
            }

            updateDirectoryContents("")
            // After download and unzip complete, notify via callback
            onDownloadComplete?.invoke()
            // Set download state to false once done
            isDownloading = false
        }

        return downloadedFilePath
    }

    // Simulate the download task and send progress updates
    private suspend fun performDownload(url: String, file: java.io.File) {
        // 10 steps to reach 50% progress which will mean that the downloading part is complete
        for (i in 1..10) {
            progressChannel.send(i * 5)
            kotlinx.coroutines.delay(500)
        }
    }

    // Simulate the unzip task and send progress updates
    private suspend fun performUnzip(file: java.io.File, directoryName: String) {
        // 10 steps to reach 100% progress (after download is complete) which will mean that the unzipping part is complete
        for (i in 1..10) {
            progressChannel.send(50 + i * 5)
            kotlinx.coroutines.delay(500)
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

    // Collect progress and update the totalProgress state
    fun collectProgressUpdates() {
        viewModelScope.launch(Dispatchers.Main) {
            for (progress in progressChannel) {
                totalProgress = progress
            }
        }
    }
}