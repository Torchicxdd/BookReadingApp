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
import com.example.bookreadingapp.data.download.FileDownload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.io.IOException

private const val TAG_DVM = "DownloadViewModel"
class DownloadViewModel(private val repository: FileDownload) : ViewModel() {
    private val _directoryContents = MutableLiveData<List<String>>()
    val directoryContents: LiveData<List<String>> = _directoryContents

    private val _progressMessage = MutableLiveData<String>()
    val progressMessage: LiveData<String> = _progressMessage

    private val _progressPercentage = mutableStateOf(0)
    val progressPercentage: MutableState<Int> get() = _progressPercentage

    private val _isDownloading = mutableStateOf(false)
    val isDownloading: Boolean get() = _isDownloading.value

    // Function to set up file download
    // Returns the absolute path of the downloaded and extracted html file
    fun setupDownload(url: String, directoryName: String) : String {
        var downloadedFilePath = ""

        viewModelScope.launch(Dispatchers.IO) {
            val fileName = url.substringAfterLast("/")
            val file = repository.createFile(directoryName, fileName)

            // Initialize progress
            _progressMessage.postValue("Preparing to download...")
            _progressPercentage.value = 0
            _isDownloading.value = true

            // Download zip file from url with progress update
            if (repository.downloadFile(url, file) { progress ->
                    _progressPercentage.value = progress
                }
            ) Log.i(TAG_DVM, "File Downloaded")
            else Log.e(TAG_DVM, "Failed to download file")

            // Extract zip file after downloading
            try {
                downloadedFilePath = repository.unzipFile(file, directoryName){ progress ->
                    _progressPercentage.value = progress
                }
            } catch(e: IOException) {
                e.printStackTrace()
                e.message?.let { Log.e(TAG_DVM, it) }
            }

            _isDownloading.value = false
            updateDirectoryContents("")
        }

        return downloadedFilePath
    }

    private suspend fun updateDirectoryContents(directoryName: String) {
        val contents = repository.listDirectoryContents(directoryName)
        _directoryContents.postValue(contents)
    }
}