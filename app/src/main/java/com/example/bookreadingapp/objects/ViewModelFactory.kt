package com.example.bookreadingapp.objects

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookreadingapp.downloads.FileDownload

class DownloadViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DownloadViewModel::class.java)) {
            val repository = FileDownload(context)
            return DownloadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
