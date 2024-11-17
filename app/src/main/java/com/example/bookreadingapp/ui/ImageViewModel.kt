package com.example.bookreadingapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookreadingapp.data.BooksAppRoomDatabase
import com.example.bookreadingapp.data.entities.Image
import com.example.bookreadingapp.data.repositories.ImageRepository

class ImageViewModel(application: Application) : ViewModel() {
    val searchedResults: MutableLiveData<List<Image>>
    private val repository: ImageRepository

    init {
        val imageDb = BooksAppRoomDatabase.getInstance(application)
        val imageDao = imageDb.imageDao()
        repository = ImageRepository(imageDao)

        searchedResults = repository.searchResults
    }

    fun insertImage(image: Image) {
        repository.insertImage(image)
    }

    fun deleteImage(id: Int) {
        repository.deleteImage(id)
    }

    fun findImageById(id: Int) {
        repository.findImageById(id)
    }
}