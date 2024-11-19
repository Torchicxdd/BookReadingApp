package com.example.bookreadingapp.download

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

private const val TAG_DVM = "DownloadViewModel"
class DownloadViewModel(private val repository: FileDownload) : ViewModel() {
    private val _directoryContents = MutableLiveData<List<String>>()
    val directoryContents: LiveData<List<String>> = _directoryContents

    // Function to set up file download
    // Returns the absolute path of the downloaded and extracted html file
    fun setupDownload(url: String, directoryName: String) : String {
        var downloadedFilePath = ""
        viewModelScope.launch(Dispatchers.IO) {
            val fileName = url.substringAfterLast("/")
            val file = repository.createFile(directoryName, fileName)

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
        }

        return downloadedFilePath;
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